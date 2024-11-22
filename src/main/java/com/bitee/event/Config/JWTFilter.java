package com.bitee.event.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    Claims claims= null;

    private String username = null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/auth/login|/auth/signup|/otp/verify|/otp/regenerate|/auth/password/change|/auth/password/forget")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);

                try {

                    username = jwtUtil.extractUsername(token);
                    claims = jwtUtil.extractAllClaims(token);
                } catch (ExpiredJwtException e) {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired. Please log in again.");
                    return;
                } catch (Exception e) {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token. Please log in again.");
                    return;
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            // Proceed with the filter chain
            filterChain.doFilter(request, response);
        }
    }


    public boolean isAdmin() {return "admin".equalsIgnoreCase((String) claims.get("role"));}

    public boolean isUser(){return "user".equalsIgnoreCase((String) claims.get("role"));}

    public String getCurrentUser() {return username;}
}
