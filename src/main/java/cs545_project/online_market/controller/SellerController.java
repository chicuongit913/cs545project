package cs545_project.online_market.controller;


import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SellerController {

    private ProductService productService;

    @Autowired
    public SellerController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/seller")
    public String getPage(Model model) {
        ArrayList<Product> products = productService.getAll();
//        model.addAttribute("sellerProducts", products);
        model.addAttribute("sellerProducts", createDummyProducts());
        return "views/seller/SellerHome";
    }

    private List<ProductResponse> createDummyProducts() {
        return IntStream.range(1, 12)
                .mapToObj(n -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setName("Apple 15.4\" MacBook Pro w/Touch Bar (Mid 2019)");
                    productResponse.setDescription("About this item\n" +
                            "15.4-inch (diagonal) LED-backlit display with IPS technology; 2880-by-1800 resolution at 220 pixels per inch with support for millions of colors");
                    productResponse.setPrice(2223 * n);
                    productResponse.setId(n);
                    productResponse.setImage("https://m.media-amazon.com/images/I/41795QZGfYL._AC_SL260_.jpg");
                    return productResponse;
                })
                .collect(Collectors.toList());
    }


    @GetMapping("/seller/addProduct")
    public String getAll(Model model) {
        model.addAttribute("product", new Product());
        return "views/seller/AddProduct";
    }


    @PostMapping(value = "/seller/addProduct")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors())
            return "views/seller/AddProduct";

        productService.saveProduct(product);
//        redirectAttributes.addFlashAttribute("product", product);
        return "views/seller/SellerHome";
    }

    @PutMapping(value = "/seller/updateProduct/{productId}")
    public String updateProduct(@PathVariable long productId, @ModelAttribute("product") Product product) {
        productService.findById(productId);
        product.setId(productId);
        productService.saveProduct(product);
        return "redirect:/seller";
    }

    @DeleteMapping(value = "/seller")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.deleteProduct(product.getId());
        return "views/seller/SellerHome";
    }
}
