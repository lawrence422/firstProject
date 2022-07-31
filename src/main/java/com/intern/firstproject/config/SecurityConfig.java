package com.intern.firstproject.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.firstproject.config.filter.JwtAuthenticationFilter;
import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.service.impl.JwtServiceImpl;
import com.intern.firstproject.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler())
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler());


        http
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/setAuthority").hasAuthority("admin")
                .anyRequest().authenticated();


        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
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
    public JwtServiceImpl jwtService() {
        return new JwtServiceImpl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    AuthenticationSuccessHandler customLoginSuccessHandler() {
        return (request, response, authentication) -> {
            // run custom logics upon successful login
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService().generateToken(userDetails);
            Map<String, String> responseBody = Collections.singletonMap("token", token);
            PrintWriter printWriter = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonOutput = new ObjectMapper().writeValueAsString(JsonResult.success(responseBody));
            printWriter.println(jsonOutput);
            printWriter.flush();
            printWriter.close();
        };
    }

    LogoutSuccessHandler customLogoutSuccessHandler(){
        return (request, response, authentication) -> {
          if(authentication!=null&&authentication.getPrincipal()!=null){
              UserDetails userDetails = (UserDetails) authentication.getPrincipal();
              PrintWriter printWriter = response.getWriter();
              response.setContentType("application/json");
              response.setCharacterEncoding("UTF-8");
              String jsonOutput = new ObjectMapper().writeValueAsString(JsonResult.success(""));
              printWriter.println(jsonOutput);
              printWriter.flush();
              printWriter.close();
          }

        };
    }



}
