package cs545_project.online_market.controller.seller;


import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    private ProductService productService;
    private MultipartFile productImage;
    private String rootDirectory = "C:\\";

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
    public String saveProduct(@Valid @ModelAttribute("product") ProductRequest product, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "views/seller/AddProduct";
        }

         productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty())
        {
            try {
                productImage.transferTo(new File(rootDirectory + "images\\" + product.getName()+".png"));
                String path = rootDirectory + "images\\" + product.getName()+".png";
                productService.saveProduct(product, path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "views/seller/SellerHome";
    }

    @GetMapping(value = "/seller/update-product/{productId}")
    public String updateProductPage(@PathVariable long productId, Model model) {
        Product updateProduct =  productService.findById(productId);
        model.addAttribute("updateProduct", updateProduct);
        return "views/seller/UpdateProduct";
    }


    @PostMapping(value = "/seller/update-product/{productId}")
    public String updateProduct(@PathVariable long productId, @Valid @ModelAttribute("updateProduct") ProductRequest product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/seller/UpdateProduct";
        }

        productImage = product.getImage();
        if (productImage != null && !productImage.isEmpty())
        {
            try {
                productImage.transferTo(new File(rootDirectory + "images\\" + product.getName()+".png"));
                String path = rootDirectory + "images\\" + product.getName()+".png";
                productService.updateProduct(product, path, productId);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "views/seller/SellerHome";
    }


    @GetMapping(value = "/seller/delete-product/{productId}")
    public String updateProduct(@PathVariable long productId) {
       Product product =  productService.findById(productId);
       if (!product.canDelete()){
           System.out.println("can not deleted");
       }

        productService.deleteProduct(productId);
        return "views/seller/SellerHome";
    }
}
