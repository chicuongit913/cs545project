package cs545_project.online_market.controller.buyer;

import cs545_project.online_market.controller.request.CartRequest;
import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.domain.Cart;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
    private OrderService orderService;
    private CartService cartService;
    private UserService userService;

    @Autowired
    public BuyerController(CartService cartService, OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String get(HttpServletRequest request, Model model) {
        String cartId = Util.extractCartId(request);
        Cart cart = cartService.read(cartId);
        if (cart == null) {
            cartId = request.getSession(true).getId();
            cart = new Cart(cartId);
            cartService.create(cart);
            Util.updateCartId(request, cartId);
        }
        model.addAttribute("cart", cart);

        return "/views/buyer/cart";
    }

    @PostMapping("/cart")
    public String checkAvailability(HttpServletRequest request, CartRequest cartRequest, Model model) {
        String cartId = Util.extractCartId(request);
        Cart cart = null;
        try {
            cart = cartService.checkAndUpdateCart(cartId, cartRequest);
            model.addAttribute("cart_message", "All items are available and Total is updated!");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("cart_errors", ex.getMessage());
            cart = cartService.read(cartId);
        }

        model.addAttribute("cart", cart);
        return "/views/buyer/cart";
    }

    @PostMapping("/cart/checkout")
    public String getCheckout(HttpServletRequest request, CartRequest cartRequest, OrderRequest orderRequest, Model model) {
        String cartId = Util.extractCartId(request);
        try {
            cartService.checkAndUpdateCart(cartId, cartRequest);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("cart_errors", ex.getMessage());
            model.addAttribute("cart", cartService.read(cartId));
            return "/views/buyer/cart";
        }

        model.addAttribute("checkout_user", userService.getUserForCheckout());
        return "views/buyer/checkout";
    }

    @PostMapping("/order")
    public String placeOrder(@Valid OrderRequest orderRequest, HttpServletRequest request, Model model) {
        try {
            String cartId = Util.extractCartId(request);
            orderService.placeOrder(orderRequest, cartService.read(cartId));
            cartService.update(cartId, new Cart());
            model.addAttribute("cart", cartService.read(cartId));
        } catch (IllegalArgumentException ex) {
            model.addAttribute("place_order_errors", ex.getMessage());
            model.addAttribute("checkout_user", userService.getUserForCheckout());
            return "views/buyer/checkout";
        }

        return "/views/buyer/cart";
    }

    @GetMapping("/orders")
    public String viewOrders(HttpServletRequest request, Model model) {
        model.addAttribute("orders", orderService.getAllOrders((String) request.getAttribute("user_name")));
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
}
