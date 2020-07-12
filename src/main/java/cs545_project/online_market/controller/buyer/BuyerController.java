package cs545_project.online_market.controller.buyer;

import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.service.CartService;
import cs545_project.online_market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
    private OrderService orderService;

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
        model.addAttribute("orders", orderService.getAllOrders((String) request.getAttribute("user_name")));
        return "/views/buyer/orders";
    }
}
