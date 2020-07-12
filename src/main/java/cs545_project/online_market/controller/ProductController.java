package cs545_project.online_market.controller;

import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author knguyen93
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, ReviewRequest reviewRequest, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "views/product-details";
    }

    @PostMapping("/{id}/review")
    public String postReview(@PathVariable Long id, @Valid ReviewRequest reviewRequest, BindingResult bindingResult,
                             Model model) {
        ProductResponse productResponse = null;
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productService.getProductById(id));
            return "views/product-details";
        } else {
            productResponse = productService.postReview(id, reviewRequest);
            return "redirect:/product/" + id;
        }
    }
}
