package com.imadelfetouh.userservice.security;

import com.imadelfetouh.userservice.rabbit.RabbitConfiguration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RabbitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if(RabbitConfiguration.getInstance().getConnection() == null && !httpServletRequest.getMethod().equals("GET")) {
            httpServletResponse.setStatus(503);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
