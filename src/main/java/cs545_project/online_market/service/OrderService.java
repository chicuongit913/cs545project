package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.OrderItemResponse;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.domain.OrderDetails;
import cs545_project.online_market.domain.OrderStatus;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * @author knguyen93
 */
public interface OrderService {
    List<OrderResponse> getOrdersOfCurrentUser();
    Order findById(long id);
    Order cancelOrder(Order order, Long itemId);
    OrderResponse placeOrder(OrderRequest request, Cart cart);
    String generateInvoiceOrder(Order order);
    List<OrderItemResponse> getCreatedOrders();
    void updateStatusOrderBySeller(Long orderDetailId, OrderStatus status);
}
