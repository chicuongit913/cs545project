package cs545_project.online_market.controller;

import cs545_project.online_market.domain.BillingAddress;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController  {

    @Autowired
    ProductService productService;

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("products", this.productService.getAllProducts());
        return "views/index";
    }
}
