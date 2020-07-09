package cs545_project.online_market.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    public static boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }
}

