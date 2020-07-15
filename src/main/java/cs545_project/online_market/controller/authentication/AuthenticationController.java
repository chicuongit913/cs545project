package cs545_project.online_market.controller.authentication;

import cs545_project.online_market.controller.request.ClaimTokenRequest;
import cs545_project.online_market.controller.request.UserRequest;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/claim-token")
    @ResponseBody
    public String claimToken(@RequestBody @Valid ClaimTokenRequest claimTokenRequest) {
        return "success";
    }

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
        return "views/authentication/login";
    }

    @GetMapping(value = "/seller_register")
    public String getSellerRegister(@ModelAttribute("userRequest") UserRequest userRequest, BindingResult bindingResult, Model model){
        model.addAttribute("create_user_role", UserRole.SELLER.getName());
        return "views/authentication/userRegisterForm";
    }

    @PostMapping(value = "/seller_register")
    public String postSellerRegister(@Valid @ModelAttribute("userRequest") UserRequest userRequest,
                                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors())
            return "views/authentication/userRegisterForm";

        User user = this.userService.createSeller(userRequest);

        redirectAttributes.addFlashAttribute("user" , user);

        // Redirect to success page (avoid the same register)
        return "redirect:/auth/success_register";
    }

    @GetMapping(value = "/buyer_register")
    public String getBuyerRegister(@ModelAttribute("userRequest") UserRequest userRequest, BindingResult bindingResult, Model model){
        model.addAttribute("create_user_role", UserRole.BUYER.getName());
        return "views/authentication/userRegisterForm";
    }

    @PostMapping(value = "/buyer_register")
    public String postBuyerRegister(@Valid @ModelAttribute("userRequest") UserRequest userRequest,
                                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors())
            return "views/authentication/userRegisterForm";

        User user = this.userService.createBuyer(userRequest);

        redirectAttributes.addFlashAttribute("user" , user);
        // Redirect to success page (avoid the same register)
        return "redirect:/auth/success_register";
    }

    @GetMapping("/success_register")
    public String getSuccessRegister(){
        return "views/authentication/successRegister";
    }

    @GetMapping("/denied")
    public String accessDenied(){
        return "views/authentication/accessDenied";
    }
}
