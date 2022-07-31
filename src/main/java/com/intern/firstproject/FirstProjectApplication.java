package com.intern.firstproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
@EnableJdbcHttpSession
@MapperScan("com.intern.firstproject.mapper")
public class FirstProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstProjectApplication.class, args);
    }

}

