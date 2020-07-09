package cs545_project.online_market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Qualifier("JPAUserDetailService")
    @Autowired
    UserDetailsService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/create").authenticated()
//                .antMatchers("/edit/**").authenticated()
//                .antMatchers("/delete/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/auth/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/perform_logout") //change default /logout url to /perform_logout
                    .logoutSuccessUrl("/auth/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/auth/denied")
                .and()
                    .rememberMe()
                    .rememberMeParameter("keepMe").tokenRepository(tokenRepository());

        //Those two settings below is to enable access h2 database via browser
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }

    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
