package cs545_project.online_market.controller;


import cs545_project.online_market.domain.Product;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private ProductService productService;

    @Autowired
    public SellerController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public String getPage(Model model){
        model.addAttribute(new Product());
        return "views/seller/AddProduct";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute("product") Product product, HttpServletRequest request){
//        MultipartFile productImage = (MultipartFile) product.getImages2();
//        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
//        if (productImage != null && !productImage.isEmpty())
//        {
//            try {
//                productImage.transferTo(new File(rootDirectory + "resources\\images\\" + product.getId()+".png"));
                productService.saveProduct(product);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return "views/seller/AllProducts";
    }

    @GetMapping("seller/allProducts")
    public String getAll(Model model){
        ArrayList<Product> products= productService.getAll();
        model.addAttribute("products", products);
        return "views/seller/AllProducts";
    }

}
