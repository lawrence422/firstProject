package com.intern.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestRedirectController {
    @RequestMapping("/home")
    public String hello(){
        System.out.println("hello home");
        return "redirect:home.html";
    }

}