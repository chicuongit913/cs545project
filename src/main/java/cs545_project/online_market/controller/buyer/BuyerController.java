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
    private CartService cartService;
    private OrderService orderService;

    @Autowired
    public BuyerController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @RequestMapping("/cart")
    public String viewCart(HttpServletRequest request, Model model) {
        String cartId = request.getSession(true).getId();
        model.addAttribute("cartId", cartId);
        Cart cart = cartService.read(cartId);
        if (cart == null) {
            cart = new Cart(cartId);
            cartService.create(cart);
        }
        model.addAttribute("cart", cart);

        return "/views/buyer/cart";
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
