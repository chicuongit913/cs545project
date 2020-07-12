package cs545_project.online_market.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/buyer/checkout")
public class CheckoutController {

    @GetMapping
    public String getCheckout(){
        return "views/buyer/checkout";
    }
}
