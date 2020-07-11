package cs545_project.online_market.controller;

import cs545_project.online_market.controller.response.ProductResponse;
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

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("products", createDummyProducts());
        return "views/index";
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
}
