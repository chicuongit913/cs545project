package cs545_project.online_market.controller.seller;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private static  String rootDirectory = System.getProperty("user.dir") + "\\images\\";

    private Util util;
    private ProductService productService;

    @Autowired
    public SellerController(Util util, ProductService productService) {
        this.util = util;
        this.productService = productService;
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
                productService.updateProduct(product, path, seller);

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
    public String updateProduct(@PathVariable long productId, Model model) {
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

}
