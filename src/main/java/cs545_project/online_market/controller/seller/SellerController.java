package cs545_project.online_market.controller.seller;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.OrderService;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private static  String rootDirectory = System.getProperty("user.dir") + "\\images\\";

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
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }

    @GetMapping("/addProduct")
    public String getAll(Model model) {
        model.addAttribute("product", new Product());
        return "views/seller/AddProduct";
    }

    @PostMapping(value = "/addProduct")
    public String saveProduct(@Valid @ModelAttribute("product") ProductRequest product, BindingResult bindingResult, HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "views/seller/AddProduct";
        }

        MultipartFile productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(rootDirectory + product.getName() + ".png"));
                String path = rootDirectory + product.getName() + ".png";
                User seller = util.getCurrentUser();
                productService.saveProduct(product, path, seller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String path = "https://placeimg.com/500/500/tech?query=97";
            User seller = util.getCurrentUser();
            productService.saveProduct(product, path, seller);
        }
        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }

    @GetMapping(value = "/update-product/{productId}")
    public String updateProductPage(@PathVariable long productId, Model model) {
        Product updateProduct = productService.findById(productId);
        model.addAttribute("updateProduct", updateProduct);
        return "views/seller/UpdateProduct";
    }

    @PostMapping(value = "/update-product/{productId}")
    public String updateProduct(@PathVariable long productId, @Valid @ModelAttribute("updateProduct") ProductRequest product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "views/seller/UpdateProduct";
        }

        MultipartFile productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty()) {
            try {
                String imageName = util.generateImageName();
                productImage.transferTo(new File(rootDirectory + product.getName() + ".png"));
                String path = rootDirectory + product.getName() + ".png";
                product.setId(productId);
                User seller = util.getCurrentUser();
                Product p=productService.findById(productId);
                System.out.println(p.getCreatedDate());
                productService.updateProduct(product, path, seller, p.getCreatedDate());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }


    @GetMapping(value = "/delete-product/{productId}")
    public String deleteProduct(@PathVariable long productId, Model model) {
        Product product = productService.findById(productId);

        if (product.isInUse() == true) {
            System.out.println("can not deleted");
            model.addAttribute("product_error", "Can not deleted already in use");
        } else {
            productService.deleteProduct(productId);
        }
        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
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
