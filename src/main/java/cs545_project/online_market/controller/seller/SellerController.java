package cs545_project.online_market.controller.seller;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.OrderService;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private Util util;
    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public SellerController(Util util, ProductService productService, OrderService orderService) {
        this.util = util;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getPage(Model model) {
        Long id = util.getCurrentUser().getId();
        model.addAttribute("sellerProducts", productService.getSellerProducts(id));
        return "views/seller/seller-home";
    }

    @GetMapping("/add-product")
    public String getAll(Model model) {
        model.addAttribute("product", new Product());
        return "views/seller/add-product";
    }

    @PostMapping(value = "/add-product")
    public String saveProduct(@Valid @ModelAttribute("product") ProductRequest product,
                              BindingResult bindingResult, HttpServletRequest request,
                              Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/seller/add-product";
        }

        try {
            productService.saveProduct(product);
        } catch (IOException e) {
            model.addAttribute("product_error", "Unable to add product");
            return "views/seller/add-product";
        }

        redirectAttributes.addFlashAttribute("message",
            String.format("%s has been added successfully", product.getName()));

        return "redirect:/seller";
    }

    @GetMapping(value = "/update-product/{productId}")
    public String updateProductPage(@PathVariable long productId, Model model) {
        Product updateProduct = productService.findById(productId);
        model.addAttribute("updateProduct", updateProduct);
        return "views/seller/update-product";
    }

    @PostMapping(value = "/update-product/{productId}")
    public String updateProduct(@PathVariable long productId,
                                @Valid @ModelAttribute("updateProduct") ProductRequest product,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "views/seller/update-product";
        }

        try {
            product.setId(productId);
            productService.updateProduct(product);
        } catch (IOException e) {
            model.addAttribute("product_error", "Unable to update product");
            return "views/seller/update-product";
        }

        redirectAttributes.addFlashAttribute("message",
            String.format("%s has been updated successfully", product.getName()));

        return "redirect:/seller";
    }

    @GetMapping(value = "/delete-product/{productId}")
    public String deleteProduct(@PathVariable long productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(productId);
            redirectAttributes.addFlashAttribute("message", "Product has been deleted successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("product_error", ex.getMessage());
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/orders")
    public String createdOrders(Model model) {
        model.addAttribute("OrderItemsResponse", orderService.getCreatedOrders());
        return "views/seller/created_orders";
    }

    @GetMapping("/orders/update_status")
    public String updateStatusForOrder(@RequestParam("id") Long id,
                                       @RequestParam("status") OrderStatus status,
                                       Model model,
                                       HttpServletRequest request) {
        this.orderService.updateStatusOrderBySeller(id, status);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
