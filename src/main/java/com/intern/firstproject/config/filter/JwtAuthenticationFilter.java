package com.intern.firstproject.config.filter;

import com.intern.firstproject.service.impl.JwtServiceImpl;
import com.intern.firstproject.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * This class is used to filter login by jwt token
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader!=null){
            String accessToken=authHeader.replace("Bearer ","");
            String username= jwtService.getUsername(accessToken);
//            System.out.println(username);
            UserDetails userDetails= userDetailService.loadUserByUsername(username);

            Authentication authentication=
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}
