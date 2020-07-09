package cs545_project.online_market.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse arg1, Object arg2) throws Exception {


        if(request.getUserPrincipal() != null) {
            List<String>  roles = this.getRolePrincipal(request.getUserPrincipal());
            String username = request.getUserPrincipal().getName();
            request.setAttribute("user_name", username);
            request.setAttribute("user_role", roles.get(0).replaceFirst("ROLE_", ""));
        }

        return true;
    }

    private List<String>  getRolePrincipal(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
