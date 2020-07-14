package cs545_project.online_market.controller.rest;

import cs545_project.online_market.domain.Product;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/all-products")
public class AllProducts {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ArrayList<Product> getAllProducts() {
        return null;
    }
}
