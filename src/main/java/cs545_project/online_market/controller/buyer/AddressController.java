package cs545_project.online_market.controller.buyer;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.domain.Address;
import cs545_project.online_market.exception.AddressNotFoundException;
import cs545_project.online_market.exception.ProductNotFoundException;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/buyer/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    Util util;

    @GetMapping("/create")
    public String getCreateAddress(@ModelAttribute("addressRequest") AddressRequest addressRequest,
                                @RequestParam("type") String type, Model model){
        model.addAttribute("type", type);
        return "/views/buyer/address";
    }

    @PostMapping("/create")
    public String postCreateAddress(@Valid @ModelAttribute("addressRequest") AddressRequest addressRequest,
                BindingResult bindingResult, @RequestParam("type") String type, Model model,
                                    RedirectAttributes redirectAttributes){

        model.addAttribute("type", type);

        if(bindingResult.hasErrors())
            return "/views/buyer/address";

        this.addressService.createOrUpdate(addressRequest, type, util.getCurrentUser());
        redirectAttributes.addFlashAttribute("successMessage", "Address is created!");
        return "redirect:/buyer/setting";
    }

    @GetMapping("/edit/{id}")
    public String getEditAddress(@PathVariable("id") long id, Model model){
        Address address = this.addressService.findById(id);

        //throw address not found exception when address = null
        if(address == null)
            throw new IllegalArgumentException(new AddressNotFoundException(id));

        if(!address.getUser().getId().equals(util.getCurrentUser().getId()))
            return "redirect:/auth/denied";

        AddressRequest addressRequest = new AddressRequest();

        BeanUtils.copyProperties(address,addressRequest);
        model.addAttribute("addressRequest", addressRequest);

        return "/views/buyer/address";
    }

    @PostMapping("/edit/{id}")
    public String postEditAddress(@Valid @ModelAttribute("addressRequest") AddressRequest addressRequest,
                                  BindingResult bindingResult, @PathVariable("id") long id, Model model,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors())
            return "/views/buyer/address";

        Address address = this.addressService.findById(id);

        //throw address not found exception when address = null
        if(address == null)
            throw new IllegalArgumentException(new AddressNotFoundException(id));

        if(!address.getUser().getId().equals(util.getCurrentUser().getId()))
            return "redirect:/auth/denied";

        BeanUtils.copyProperties(addressRequest, address);
        this.addressService.save(address);

        redirectAttributes.addFlashAttribute("successMessage", "Address is updated!");
        return "redirect:/buyer/setting";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAddress(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes){

        Address address = this.addressService.findById(id);

        //throw address not found exception when address = null
        if(address == null)
            throw new IllegalArgumentException(new AddressNotFoundException(id));

        if(!address.getUser().getId().equals(util.getCurrentUser().getId()))
            return "redirect:/auth/denied";

        try{
            this.addressService.delete(address);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Address can not delete because its using for order!");
            return "redirect:/buyer/setting";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Address is deleted!");
        return "redirect:/buyer/setting";
    }

}
