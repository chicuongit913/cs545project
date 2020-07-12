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
import cs545_project.online_market.domain.ShippingAddress;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.repository.OrderRepository;
import cs545_project.online_market.repository.ProductRepository;
import cs545_project.online_market.repository.UserRepository;
import org.hashids.Hashids;
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
    private Hashids hashids;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, Util util, Hashids hashids) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.util = util;
        this.hashids = hashids;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request, Cart cart) {
        User buyer = Optional.ofNullable(util.getCurrentUser())
            .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        if (request.isApplyCoupon() && buyer.getPoints() == 0) {
            throw new IllegalArgumentException("No coupon available for Buyer");
        }

        Order order = generateOrder(buyer, request, cart);

        if (request.isApplyCoupon()) {
            double total = order.total(), credit = 0, remainPoints = 0;
            if (total <= buyer.getPoints()) {
                remainPoints = buyer.getPoints() - total;
            } else {
                credit = order.total() - buyer.getPoints();
                order.setCard(
                    buyer.getCards()
                        .stream()
                    .filter(c -> c.getId() == request.getPaymentCard())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Payment Card"))
                );
            }

            buyer.setPoints(remainPoints);
            order.setCredit(credit);
        }

        buyer.addOrder(order);
        userRepository.save(buyer);
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders(String username) {
        User buyer = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        return buyer.getOrders()
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

        ShippingAddress shippingAddress = buyer.getShippingAddresses()
            .stream()
            .filter(addr -> addr.getId() == request.getShippingAddress())
            .findFirst()
            .orElseGet(() -> {
                ShippingAddress address = new ShippingAddress();
                BeanUtils.copyProperties(request.getShippingAddress(), address);
                return address;
            });

        BillingAddress billingAddress = buyer.getBillingAddresses()
            .stream()
            .filter(addr -> addr.getId() == request.getBillingAddress())
            .findFirst()
            .orElseGet(() -> {
                BillingAddress address = new BillingAddress();
                BeanUtils.copyProperties(request.getBillingAddress(), address);
                return address;
            });
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setOrderDetails(items);

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
                return new OrderDetails(prod, cartItem.getQuantity(), prod.getPrice());
            })
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderCode(hashids.encode(order.getId()));
        orderResponse.setBillingAddress(this.mapToBillingAddressResponse(order.getBillingAddress()));
        orderResponse.setShippingAddress(this.mapToShippingAddressResponse(order.getShippingAddress()));
        orderResponse.setCredit(order.getCredit());
        orderResponse.setPoints(order.getPoints());
        orderResponse.setTotal(order.total());
        orderResponse.setOrderItems(
            order.getOrderDetails()
                .stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toList())
        );
        return orderResponse;
    }

    private OrderItemResponse mapToOrderItemResponse(OrderDetails orderDetails) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setPrice(orderDetails.getPrice());
        orderItemResponse.setQuantity(orderDetails.getQuantity());
        orderItemResponse.setProductName(orderDetails.getProduct().getName());
        orderItemResponse.setImage(orderDetails.getProduct().getImage());
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
}
