package com.example.careerhub.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("USER")) {
                response.sendRedirect("/employer-home");
                return;
            } else if (authority.getAuthority().equals("SEEKER")) {
                response.sendRedirect("/seeker-home");
                return;
            }  else if (authority.getAuthority().equals("ADMIN")) {
            response.sendRedirect("/admin-home");
            return;
        }
            // Add more roles if needed
        }
        // Default redirection if no matching role is found
        response.sendRedirect("/index");
    }
}
