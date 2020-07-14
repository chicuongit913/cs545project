package cs545_project.online_market.config;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs545_project.online_market.controller.request.ClaimTokenRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static cs545_project.online_market.config.SecurityConstants.EXPIRATION_TIME;
import static cs545_project.online_market.config.SecurityConstants.HEADER_STRING;
import static cs545_project.online_market.config.SecurityConstants.SECRET;
import static cs545_project.online_market.config.SecurityConstants.TOKEN_PREFIX;

/**
 * @author knguyen93
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RequestMatcher requestMatcher;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String pattern) {
        this.authenticationManager = authenticationManager;
        this.requestMatcher = new AntPathRequestMatcher(pattern);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        if (requestMatcher.matches(request)) {
            Authentication authentication = this.attemptAuthentication(request, response);
            this.successfulAuthentication(request, response, chain, authentication);
        } else {
            super.doFilter(req, res, chain);
        }

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ClaimTokenRequest creds = new ObjectMapper()
                .readValue(req.getInputStream(), ClaimTokenRequest.class);
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = JWT.create()
            .withSubject(((JPAUserDetails) auth.getPrincipal()).getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
