package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Cart;

import java.util.List;

/**
 * @author knguyen93
 */
public interface OrderService {
    OrderResponse placeOrder(OrderRequest request, Cart cart);
    List<OrderResponse> getAllOrders(String username);
}
