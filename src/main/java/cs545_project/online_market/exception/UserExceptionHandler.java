package cs545_project.online_market.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(AddressNotFoundException.class) public ModelAndView handleError(HttpServletRequest req,
                                                                                      AddressNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", exception.getMessage());
        mav.setViewName("/views/buyer/userNotFound");
        return mav;
    }
}
