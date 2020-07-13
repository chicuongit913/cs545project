package cs545_project.online_market.controller.buyer;

import cs545_project.online_market.domain.Order;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.exception.UserNotFoundException;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.CartService;
import cs545_project.online_market.service.OrderService;
import cs545_project.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    Util util;

    @Autowired
    public BuyerController(CartService cartService, OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String getCheckout() {
        return "views/buyer/checkout";
    }

    @GetMapping("/orders")
    public String viewOrders(HttpServletRequest request, Model model) {
        model.addAttribute("orders", orderService.getOrdersOfCurrentUser());
        return "/views/buyer/orders";
    }

    @GetMapping("/follow_seller/{id}")
    public String followSeller(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        User seller = userService.findById(id);

        if(seller == null || seller.getRole() != UserRole.SELLER)
            throw new IllegalArgumentException(new UserNotFoundException(id));

        userService.followSeller(seller);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping("/un_follow_seller/{id}")
    public String unFollowSeller(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        User seller = userService.findById(id);

        if(seller == null || seller.getRole() != UserRole.SELLER)
            throw new IllegalArgumentException(new UserNotFoundException(id));

        userService.unFollowSeller(seller);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);

        if (order == null) {
            return "redirect:/buyer/orders";
        }

        if(!order.getBuyer().equals(util.getCurrentUser()) || order.getStatus() != OrderStatus.NEW)
             return "redirect:/auth/denied";

        orderService.cancelOrder(order);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }


}
