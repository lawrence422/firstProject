package com.intern.firstproject.config;


import com.intern.firstproject.config.handler.LoginSuccessHandler;
import com.intern.firstproject.filter.JwtAuthenticationFilter;
import com.intern.firstproject.service.impl.JwtServiceImpl;
import com.intern.firstproject.service.impl.UserDetailServiceImpl;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailServiceImpl userDetailsServiceImpl;

//    @Autowired
//    @Lazy
//    LoginSuccessHandler loginSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
//                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    // run custom logics upon successful login

                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();
                    String token= jwtService().generateToken(userDetails);
                    System.out.println("The user " + username + " has logged in.");
                    System.out.println(token);
                    PrintWriter printWriter=response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    printWriter.println("{\n\t\"token\": \""+token+"\"\n}");
                    printWriter.flush();
                    printWriter.close();
                }); //port


        http
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/auth").permitAll()
                .anyRequest().authenticated();


        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(new BCryptPasswordEncoder());

    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtServiceImpl jwtService(){
        return new JwtServiceImpl();
    }


}
