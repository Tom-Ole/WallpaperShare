package com.taskmanager.taskmanager.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.taskmanager.taskmanager.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                    if (savedRequest != null) {
                        String targetUrl = savedRequest.getRedirectUrl();
                        // Cut out ?continue= if it exists
                        if (targetUrl.contains("?continue")) {
                            targetUrl = targetUrl.substring(0, targetUrl.indexOf("?continue"));
                        }
                        response.sendRedirect(targetUrl);
                    } else {
                        response.sendRedirect("/");
                    }
                }));

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
