package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.domain.Cart;
import java.util.List;

/**
 * @author knguyen93
 */
public interface OrderService {
    List<OrderResponse> getOrdersOfCurrentUser();
    Order findById(long id);
    Order cancelOrder(Order order);
    OrderResponse placeOrder(OrderRequest request, Cart cart);
    String generateInvoiceOrder(Order order);
}
