package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.AddressResponse;
import cs545_project.online_market.controller.response.OrderItemResponse;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.BillingAddress;
import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.domain.CartItem;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.domain.OrderDetails;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.domain.ShippingAddress;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.repository.OrderRepository;
import cs545_project.online_market.repository.ProductRepository;
import cs545_project.online_market.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author knguyen93
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private Util util;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, Util util) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.util = util;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request, Cart cart) {
        User buyer = Optional.ofNullable(util.getCurrentUser())
            .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        if (request.isApplyCoupon() && buyer.getPoints() == 0) {
            throw new IllegalArgumentException("No coupon available for Buyer");
        }

        Order order = generateOrder(buyer, request, cart);

        double total = order.total(), credit = 0, remainPoints = 0;
        if (request.isApplyCoupon()) {
            if (total <= buyer.getAvailablePointsCredit()) {
                remainPoints = buyer.getAvailablePointsCredit() - total;
            } else {
                credit = order.total() - buyer.getAvailablePointsCredit();
            }
        } else {
            credit = total;
        }

        order.setCard(
            buyer.getCards()
                .stream()
                .filter(c -> c.getId().equals(request.getPaymentCard()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Payment Card"))
        );
        buyer.setPoints(remainPoints * 100 + credit);
        order.setCredit(credit);
        order.setBuyer(buyer);
        buyer.addOrder(order);
        userRepository.save(buyer); // Save Order
        productRepository.saveAll(
            order.getOrderDetails()
                .stream()
                .map(OrderDetails::getProduct)
                .collect(Collectors.toList())); // Update Products Stock
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersOfCurrentUser() {
        return util.getCurrentUser().getOrders()
            .stream()
            .map(this::mapToOrderResponse)
            .collect(Collectors.toList());
    }

    private Order generateOrder(User buyer, OrderRequest request, Cart cart) {
        Order order = new Order();
        List<OrderDetails> items = cart.getCartItems()
            .values()
            .stream()
            .map(this::generateOrderDetails)
            .collect(Collectors.toList());
        order.setOrderDetails(items);

        ShippingAddress shippingAddress = buyer.getShippingAddresses()
            .stream()
            .filter(addr -> addr.getId().equals(request.getShippingAddress()))
            .findFirst()
            .orElseGet(() -> {
                ShippingAddress address = new ShippingAddress();
                BeanUtils.copyProperties(request.getShippingAddress(), address);
                return address;
            });

        BillingAddress billingAddress = buyer.getBillingAddresses()
            .stream()
            .filter(addr -> addr.getId().equals(request.getBillingAddress()))
            .findFirst()
            .orElseGet(() -> {
                BillingAddress address = new BillingAddress();
                BeanUtils.copyProperties(request.getBillingAddress(), address);
                return address;
            });
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setReceiver(request.getReceiver());
        return order;
    }

    private OrderDetails generateOrderDetails(CartItem cartItem) {
        return this.productRepository
            .findById(cartItem.getProduct().getId())
            .map(prod -> {
                if (prod.getStock() < cartItem.getQuantity()) {
                    throw new IllegalArgumentException(
                        String.format("Product name %s only has %d item left", prod.getName(), prod.getStock()));
                }

                prod.setStock(prod.getStock() - cartItem.getQuantity());
                return new OrderDetails(prod, cartItem.getQuantity(), prod.getPrice());
            })
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse, "id", "shippingAddress", "billingAddress");
        orderResponse.setOrderCode(util.generateOrderCode(order.getId()));
        orderResponse.setBillingAddress(this.mapToBillingAddressResponse(order.getBillingAddress()));
        orderResponse.setShippingAddress(this.mapToShippingAddressResponse(order.getShippingAddress()));
        orderResponse.setEarnedPoints(order.getCredit());
        orderResponse.setOrderItems(
            order.getOrderDetails()
                .stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toList())
        );
        orderResponse.setTotal(order.total());
        return orderResponse;
    }

    private OrderItemResponse mapToOrderItemResponse(OrderDetails orderDetails) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setPrice(orderDetails.getPrice());
        orderItemResponse.setQuantity(orderDetails.getQuantity());
        orderItemResponse.setProductName(orderDetails.getProduct().getName());
        orderItemResponse.setImage(orderDetails.getProduct().getImage());
        orderItemResponse.setProductId(orderDetails.getProduct().getId());
        return orderItemResponse;
    }

    private AddressResponse mapToBillingAddressResponse(BillingAddress billingAddress) {
        AddressResponse response = new AddressResponse();
        BeanUtils.copyProperties(billingAddress, response);
        return response;
    }

    private AddressResponse mapToShippingAddressResponse(ShippingAddress shippingAddress) {
        AddressResponse response = new AddressResponse();
        BeanUtils.copyProperties(shippingAddress, response);
        return response;
    }

    public Order findById(long id) {
        return this.orderRepository.findById(id).isPresent()?this.orderRepository.findById(id).get():null;
    }

    public Order cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELED);
        return  this.orderRepository.save(order);
    }
}
