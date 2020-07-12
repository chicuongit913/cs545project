package cs545_project.online_market.controller.buyer;

import javax.servlet.http.HttpServletRequest;

import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/buyer/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    static final String CART_ID_SESSION_KEY = "CART_ID_SESSION_KEY";

    @RequestMapping
    public String get(HttpServletRequest request, Model model) {
        String cartId = (String) request.getSession(true).getAttribute(CART_ID_SESSION_KEY);
        Cart cart = cartService.read(cartId);
        if (cart == null) {
            cartId = request.getSession(true).getId();
            cart = new Cart(cartId);
            cartService.create(cart);
            request.getSession(true).setAttribute(CART_ID_SESSION_KEY, cartId);
        }
        model.addAttribute("cart", cart);

        return "/views/buyer/cart";
    }
}
