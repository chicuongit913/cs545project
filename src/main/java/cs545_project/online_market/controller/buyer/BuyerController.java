package cs545_project.online_market.controller.buyer;

import com.lowagie.text.DocumentException;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.controller.request.CartRequest;
import cs545_project.online_market.controller.request.OrderRequest;
import cs545_project.online_market.controller.response.AddressResponse;
import cs545_project.online_market.controller.response.CardResponse;
import cs545_project.online_market.controller.response.CheckoutUserResponse;
import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.exception.UserNotFoundException;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.CartService;
import cs545_project.online_market.service.OrderService;
import cs545_project.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.Optional;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
    private OrderService orderService;
    private CartService cartService;
    private UserService userService;

    @Autowired
    Util util;

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

        setupCheckoutData(orderRequest, model);
        return "views/buyer/checkout";
    }

    private void setupCheckoutData(OrderRequest orderRequest, Model model) {
        CheckoutUserResponse userForCheckout = userService.getUserForCheckout();
        if (userForCheckout != null) {
            orderRequest.setReceiver(userForCheckout.getName());
            orderRequest.setBillingAddress(Optional.ofNullable(userForCheckout.getBillingAddresses()).filter(l -> !l.isEmpty()).map(addr -> addr.get(0)).orElseGet(AddressResponse::new).getId());
            orderRequest.setShippingAddress(Optional.ofNullable(userForCheckout.getShippingAddresses()).filter(l -> !l.isEmpty()).map(addr -> addr.get(0)).orElseGet(AddressResponse::new).getId());
            orderRequest.setPaymentCard(Optional.ofNullable(userForCheckout.getCards()).filter(l -> !l.isEmpty()).map(cards -> cards.get(0)).orElseGet(CardResponse::new).getId());
        }
        model.addAttribute("checkout_user", userForCheckout);
    }

    @PostMapping("/order")
    public String placeOrder(@Valid OrderRequest orderRequest, HttpServletRequest request, Model model,
                             RedirectAttributes attributes) {
        try {
            String cartId = Util.extractCartId(request);
            OrderResponse orderResponse = orderService.placeOrder(orderRequest, cartService.read(cartId));
            attributes.addFlashAttribute("points", orderResponse.getEarnedPoints());
            cartService.emptyCart(cartId);
            model.addAttribute("cart", cartService.read(cartId));
        } catch (IllegalArgumentException ex) {
            model.addAttribute("place_order_errors", ex.getMessage());
            setupCheckoutData(orderRequest, model);
            return "views/buyer/checkout";
        }

        return "redirect:/buyer/order-success";
    }

    @GetMapping("/order-success")
    public String orderedSuccessful(Model model) {
        return "views/buyer/order-success";
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

    @GetMapping("/orders/print/{id}")
    public ResponseEntity<InputStreamResource> printInvoiceOrder(@PathVariable("id") long id, Model model, HttpServletRequest request) throws IOException, DocumentException {
        Order order = orderService.findById(id);

//        if (order == null) {
//            return "redirect:/buyer/orders";
//        }
//
//        if(!order.getBuyer().equals(util.getCurrentUser()))
//            return "redirect:/auth/denied";

        String  htmlOder = this.orderService.generateInvoiceOrder(order);

        String outputFolder = "invoice"+id+".pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlOder);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename="+"invoice"+id+".pdf");

        File file = new File( outputFolder);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
