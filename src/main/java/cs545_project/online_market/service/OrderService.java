package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.OrderResponse;

import java.util.List;

/**
 * @author knguyen93
 */
public interface OrderService {
    OrderResponse makeOrder(String buyerUserName, OrderRequest request);
    List<OrderResponse> getAllOrders(String username);
}
