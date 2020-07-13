package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.OrderResponse;
<<<<<<< HEAD
import cs545_project.online_market.domain.Order;
=======
import cs545_project.online_market.domain.Cart;
>>>>>>> a59569342688b241ae998072450a4352f082a91e

import java.util.List;

/**
 * @author knguyen93
 */
public interface OrderService {
<<<<<<< HEAD
    OrderResponse makeOrder(String buyerUserName, OrderRequest request);
    List<OrderResponse> getOrdersOfCurrentUser();
    Order findById(long id);
    Order cancelOrder(Order order);
=======
    OrderResponse placeOrder(OrderRequest request, Cart cart);
    List<OrderResponse> getAllOrders(String username);
>>>>>>> a59569342688b241ae998072450a4352f082a91e
}
