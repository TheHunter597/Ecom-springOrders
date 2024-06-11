package com.springOrders.springOrdersMain.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springOrders.springOrdersMain.security.customUserDetails.JwtAuthentication;
import com.springOrders.springOrdersMain.security.customUserDetails.JwtUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String email = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            var token = authorization.substring(7);
            var decoded = jwtService.verify(token);
            email = decoded.getClaim("email").asString();
        }

        if (email != null) {
            var user = jwtUserDetailsService.loadUserByUsername(email);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }
            var authentication = new JwtAuthentication(user, true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
