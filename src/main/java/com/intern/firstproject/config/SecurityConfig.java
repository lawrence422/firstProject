package com.intern.firstproject.config;


import com.intern.firstproject.filter.LoginFilter;
import com.intern.firstproject.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //
//
//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private UserDetailServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
//                .loginPage("/login.html")
                .loginProcessingUrl("/login") //port
                .successForwardUrl("/home");

        http
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/auth","/parse").permitAll()
                .anyRequest().authenticated();

        http
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

    ////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .formLogin()
////                .loginPage("/login.html")
////                .loginProcessingUrl("/login")
////                .successForwardUrl("/home.html");
////
////        http
////                .authorizeRequests()
////                .antMatchers("/login.html").permitAll()
////                .antMatchers("/test/admin").hasAuthority("admin")
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic();
////
////        http
////                .cors()
////                .configurationSource(configurationSource())
////                .and()
////                .csrf().disable();
////    }
//
////    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .formLogin().permitAll();
//////                .loginPage("/login.html").permitAll()
//////                .loginProcessingUrl("/login")
//////                .successForwardUrl("/home")
//////                .failureForwardUrl("/loginError");
////
////        http
////                .authorizeRequests(authorize -> {
////                    try {
////                        authorize
////                                .antMatchers("/test/hello").permitAll()
////                                .antMatchers("/oauth/**").permitAll()
////                                .antMatchers("/user/login").permitAll()
////                                .antMatchers("/test/admin").hasAuthority("admin")
////                                .anyRequest().authenticated()
////                                .and()
////                                .sessionManagement()
////                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                });
////
////        http
////                .cors()
////                .configurationSource(configurationSource())
////                .and()
////                .csrf().disable();
////
//////        http.addFilterBefore(, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
//
//
////    @Bean
////    public AuthenticationManager authenticationManager(
////            AuthenticationConfiguration authConfig) throws Exception {
////        return authConfig.getAuthenticationManager();
////    }
//
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
////    @Bean
////    CorsConfigurationSource configurationSource() {
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
////        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
////        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", corsConfiguration);
////        return source;
////    }
//
//


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
