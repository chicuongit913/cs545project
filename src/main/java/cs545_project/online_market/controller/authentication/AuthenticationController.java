package cs545_project.online_market.controller.authentication;

import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        if(Util.isLogged())
            return "redirect:/";

        String errorMessge = "";
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "/views/authentication/login";
    }

    @GetMapping(value = "/seller_register")
    public String getSellerRegister(@ModelAttribute("user_seller") User user, BindingResult bindingResult, Model model){
        return "/views/authentication/sellerRegisterForm";
    }

    @PostMapping(value = "/seller_register")
    public String postSellerRegister(@Valid @ModelAttribute("user_seller") User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "/views/authentication/sellerRegisterForm";

        // Encode password string to BCryptPasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set Role seller when user register is seller
        user.setRole(UserRole.SELLER);

        // Save seller
        this.userService.create(user);

        // Redirect to success page (avoid the same register)
        return "redirect:/auth/success_register";
    }

    @GetMapping("/success_register")
    public String getSuccessRegister(){
        return "/views/authentication/SuccessRegister";
    }

    @GetMapping("/denied")
    public String accessDenied(){
        return "/views/authentication/accessDenied";
    }
}
