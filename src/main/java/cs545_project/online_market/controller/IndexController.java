package cs545_project.online_market.controller;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class IndexController  {

    @Autowired
    ProductService productService;

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("products", this.productService.getAll());
        return "views/index";
    }
}
