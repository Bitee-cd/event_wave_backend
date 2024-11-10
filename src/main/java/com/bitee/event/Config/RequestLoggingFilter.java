package com.bitee.event.Config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@WebFilter("/*")
public class RequestLoggingFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Log request method and URL
        logger.info("Incoming request: {} {}"+" "+ request.getMethod()+ " "+ request.getRequestURL());

        // Log headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} = {}"+" "+ headerName+ " "+ request.getHeader(headerName));
        }

        // Proceed with the request
        filterChain.doFilter(request, response);

        // Log response status
        logger.info("Response status: {}"+" "+ response.getStatus());
    }
}
