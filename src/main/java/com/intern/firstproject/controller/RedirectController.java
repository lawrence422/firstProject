package com.intern.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RedirectController {

//    @RequestMapping("/login")
//    public String login(){
//        return  "redirect:home.html";
//    }

    @RequestMapping("/home")
    public String hello(){
        System.out.println("hello home");
        return "redirect:home.html";
    }

}