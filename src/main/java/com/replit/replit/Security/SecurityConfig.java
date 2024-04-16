package com.replit.replit.Security;


import com.replit.replit.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(customPasswordEncoder());
        return auth;
    }
    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public SpringSecurityDialect springSecurityDialect() {
//        return new SpringSecurityDialect();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/","/customLogin","/registerUser","/signUp","/createUser").permitAll()
//                                .requestMatchers(HttpMethod.POST,"/api/filter/**").permitAll()
//                                .requestMatchers(HttpMethod.GET,"/api/Posts").permitAll()
//                                .requestMatchers(HttpMethod.PUT,"/api/Posts").hasAnyRole("AUTHOR","ADMIN")
//                                .requestMatchers(HttpMethod.POST,"/api/Posts").hasAnyRole("AUTHOR","ADMIN")
//                                .requestMatchers(HttpMethod.DELETE,"/api/Posts/").hasAnyRole("AUTHOR","ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/customLogin")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/home",true)
                                .permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/customlogin")
                                .permitAll().defaultSuccessUrl("/home",true)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );
        http.httpBasic(Customizer.withDefaults()); http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
