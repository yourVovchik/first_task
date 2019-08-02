package com.balinasoft.firsttask.config.security;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class SecurityFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint =
            new Http401AuthenticationEntryPoint("");

    public SecurityFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Access-Token");

        if (token != null) {
            try {
                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(token, token);

//                authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));

                Authentication authenticate = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authenticate);

            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                this.authenticationEntryPoint.commence(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
