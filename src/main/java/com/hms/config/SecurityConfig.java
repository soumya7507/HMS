package com.hms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfig{
    @Autowired
    private JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain (
            HttpSecurity http
    ) throws Exception{
        //hcd2
        http.csrf().disable().cors().disable();//forgery
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);//JWTFilter
        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();
//         http.authorizeHttpRequests().
//              requestMatchers("/api/v1/users/signup",
//                      "/api/v1/users/signup-property-owner",
//                      "/api/v1/users/login",
//                      "/api/v1/users/validate-login-otp").
//                permitAll().requestMatchers("/api/v1/city","/api/v1/country","/api/v1/property/addProperty",
//                         "/api/v1/rooms").
//                hasAnyRole("OWNER","ADMIN").anyRequest().authenticated();
        return http.build();
    }
}


