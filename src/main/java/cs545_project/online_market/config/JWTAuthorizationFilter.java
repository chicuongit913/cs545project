package cs545_project.online_market.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import static cs545_project.online_market.config.SecurityConstants.HEADER_STRING;
import static cs545_project.online_market.config.SecurityConstants.SECRET;
import static cs545_project.online_market.config.SecurityConstants.TOKEN_PREFIX;

/**
 * @author knguyen93
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    UserDetailsService userDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailService) {
        super(authenticationManager);
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Optional.ofNullable(req.getHeader(HEADER_STRING))
            .filter(header -> header.startsWith(TOKEN_PREFIX))
            .ifPresent((header) -> SecurityContextHolder.getContext().setAuthentication(getAuthentication(req)));
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        return Optional.ofNullable(header)
            .map(t -> t.replace(TOKEN_PREFIX, ""))
            .map(token -> JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject()
            ).map(user -> userDetailService.loadUserByUsername(user))
            .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()))
            .orElseGet(null);
    }
}
