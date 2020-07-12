package cs545_project.online_market.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/buyer/setting")
public class SettingController {

    @GetMapping
    public String getSetting(){
        return "/views/buyer/setting";
    }
}
