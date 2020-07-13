package cs545_project.online_market.helper;

import cs545_project.online_market.domain.User;
import cs545_project.online_market.service.UserService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class Util {
    public static final String CART_ID_SESSION_KEY = "CART_ID_SESSION_KEY";

    private UserService userService;
    private Hashids hashids;

    @Autowired
    public Util(UserService userService, Hashids hashids) {
        this.userService = userService;
        this.hashids = hashids;
    }

    public static boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }

    public static String generateDisplayCardNumber(String cardNumber) {
        return String.format("%s****", cardNumber.substring(0, cardNumber.length() - 4));
    }

    public User getCurrentUser() {
        if(isLogged()) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return userService.findByUsername(username).isPresent()?userService.findByUsername(username).get():null;

        }

        return null;
    }

    public static String extractCartId(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute(CART_ID_SESSION_KEY);
    }

    public static void updateCartId(HttpServletRequest request, String cartId) {
        request.getSession(true).setAttribute(CART_ID_SESSION_KEY, cartId);
    }

    public String generateOrderCode(long id) {
        return hashids.encode(id);
    }
}

