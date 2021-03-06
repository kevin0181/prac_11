package securityexample1.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import securityexample1.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Order(1)
    @Configuration
    public static class UserConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

//            http.cors().and().csrf().disable();

            http
                    .antMatcher("/user/**")
                    .authorizeRequests()
                    .antMatchers("/user/signup-page", "/user/index").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/user/login-page")
                    .loginProcessingUrl("/user/loginUser")
                    .defaultSuccessUrl("/user/home")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/index")
                    .invalidateHttpSession(true);
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new UserService();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            authenticationProvider.setUserDetailsService(userDetailsService());
            return authenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }
    }


    @Order(2)
    @Configuration
    public static class AdminConfig extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {

//            http.cors().and().csrf().disable();

            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/admin/admin-home").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/admin/admin-login")
                    .loginProcessingUrl("/admin/loginAdmin")
                    .defaultSuccessUrl("/admin/admin-home")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/admin/adminLogout")
                    .logoutSuccessUrl("/admin/admin-login")
                    .invalidateHttpSession(true);
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new UserService();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            authenticationProvider.setUserDetailsService(userDetailsService());
            return authenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

    }


}