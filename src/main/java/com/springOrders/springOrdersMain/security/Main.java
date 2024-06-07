package com.springOrders.springOrdersMain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Main {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    org.springframework.security.web.SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        var result = http.authorizeHttpRequests((a) -> {
            a.requestMatchers("/health/").permitAll();
            a.anyRequest().authenticated();
        }).csrf((csrf) -> {
            csrf.disable();
        }).sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return result.build();
    }
}
