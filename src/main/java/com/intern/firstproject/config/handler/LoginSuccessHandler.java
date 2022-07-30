package com.intern.firstproject.config.handler;

import com.intern.firstproject.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
    private static final String HEADER= "Authorization";

    @Autowired
    private JwtServiceImpl jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token=jwtService.generateToken(userDetails);
        response.setContentType("application/json");
        response.setHeader(HEADER,token);
        response.sendRedirect("/home.html");


    }
}
