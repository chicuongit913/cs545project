package cs545_project.online_market.controller.seller;


import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.ProductService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SellerController {


    private Util util;
    private ProductService productService;
    private MultipartFile productImage;
    private String rootDirectory = System.getProperty("user.dir") + "\\images\\";


    @Autowired
    public SellerController(Util util, ProductService productService) {
        this.util = util;
        this.productService = productService;
    }



    @GetMapping("/seller")
    public String getPage(Model model) {
        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }

    @GetMapping("/seller/addProduct")
    public String getAll(Model model) {
        model.addAttribute("product", new Product());
        return "views/seller/AddProduct";
    }


    @PostMapping(value = "/seller/addProduct")
    public String saveProduct(@Valid @ModelAttribute("product") ProductRequest product, BindingResult bindingResult, HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "views/seller/AddProduct";
        }

         productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty())
        {
            try {
                productImage.transferTo(new File(rootDirectory + product.getName()+".png"));
                String path = rootDirectory + product.getName()+".png";
                User seller = util.getCurrentUser();
                productService.saveProduct(product, path, seller);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            String path = "https://placeimg.com/500/500/tech?query=97";
            User seller = util.getCurrentUser();
            productService.saveProduct(product, path, seller);
        }
        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }

    @GetMapping(value = "/seller/update-product/{productId}")
    public String updateProductPage(@PathVariable long productId, Model model) {
        Product updateProduct =  productService.findById(productId);
        model.addAttribute("updateProduct", updateProduct);
        return "views/seller/UpdateProduct";
    }


    @PostMapping(value = "/seller/update-product/{productId}")
    public String updateProduct(@PathVariable long productId, @Valid @ModelAttribute("updateProduct") ProductRequest product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "views/seller/UpdateProduct";
        }

        productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty())
        {
            try {
                String imageName = util.generateImageName();
                productImage.transferTo(new File(rootDirectory + imageName+".png"));
                String path = rootDirectory + product.getName()+".png";
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


    @GetMapping(value = "/seller/delete-product/{productId}")
    public String updateProduct(@PathVariable long productId, Model model) {
       Product product =  productService.findById(productId);

       if (product.isInUse() == true){
           System.out.println("can not deleted");
           model.addAttribute("inUse","Can not deleted already in use");

       }

       else
        productService.deleteProduct(productId);
        Long id = util.getCurrentUser().getId();
        ArrayList<Product> products = productService.getSellerProducts(id);
        model.addAttribute("sellerProducts", products);
        return "views/seller/SellerHome";
    }

}
