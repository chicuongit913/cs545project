package cs545_project.online_market.helper;

import cs545_project.online_market.domain.User;
import cs545_project.online_market.service.UserService;
import cs545_project.online_market.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Util {

    @Autowired
    UserService userService;

    public static boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }

    public User getCurrentUser() {
        if(isLogged()) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return userService.findByUsername(username).isPresent()?userService.findByUsername(username).get():null;

        }

        return null;
    }
}

