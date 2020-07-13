package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.AddressResponse;
import cs545_project.online_market.controller.response.OrderItemResponse;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.*;
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
    private Hashids hashids;

    @Autowired
    Util util;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, Hashids hashids) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.hashids = hashids;
    }

    @Override
    public OrderResponse makeOrder(String buyerUserName, OrderRequest request) {
        User buyer = this.userRepository.findByUsername(buyerUserName)
            .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        if (request.isApplyCoupon() && buyer.getPoints() == 0) {
            throw new IllegalArgumentException("No coupon available for Buyer");
        }

        Order order = generateOrder(buyer, request);

        if (request.isApplyCoupon()) {
            double total = order.total(), credit = 0, remainPoints = 0;

            if (total <= buyer.getPoints()) {
                remainPoints = buyer.getPoints() - total;
            } else {
                credit = order.total() - buyer.getPoints();
            }

            buyer.setPoints(remainPoints);
            order.setCredit(credit);
        }

        buyer.addOrder(order);
        userRepository.save(buyer);
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersOfCurrentUser() {
        return util.getCurrentUser().getOrders()
            .stream()
            .map(this::mapToOrderResponse)
            .collect(Collectors.toList());
    }

    private Order generateOrder(User buyer, OrderRequest request) {
        Order order = new Order();
        List<OrderDetails> items = request.getItemRequests()
            .stream()
            .map(this::generateOrderDetails)
            .collect(Collectors.toList());

        ShippingAddress shippingAddress = buyer.getShippingAddresses()
            .stream()
            .filter(addr -> addr.getId() == request.getShippingAddress().getId())
            .findFirst()
            .orElseGet(() -> {
                ShippingAddress address = new ShippingAddress();
                BeanUtils.copyProperties(request.getShippingAddress(), address);
                return address;
            });

        BillingAddress billingAddress = buyer.getBillingAddresses()
            .stream()
            .filter(addr -> addr.getId() == request.getShippingAddress().getId())
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

    private OrderDetails generateOrderDetails(OrderRequest.ItemRequest itemRequest) {
        return this.productRepository
            .findById(itemRequest.getProductId())
            .map(prod -> {
                if (prod.getStock() < itemRequest.getQuantity()) {
                    throw new IllegalArgumentException(
                        String.format("Product name %s only has %d item left", prod.getName(), prod.getStock()));
                }
                return new OrderDetails(prod, itemRequest.getQuantity(), prod.getPrice());
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
        orderResponse.setReceiver(order.getReceiver());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCreatedDate(order.getCreatedDate());
        orderResponse.setId(order.getId());
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

    public Order findById(long id) {
        return this.orderRepository.findById(id).isPresent()?this.orderRepository.findById(id).get():null;
    }

    public Order cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELED);
        return  this.orderRepository.save(order);
    }
}
