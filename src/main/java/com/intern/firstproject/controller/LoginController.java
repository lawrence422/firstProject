package com.intern.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

//    @RequestMapping("/login")
//    public String login(){
//        return  "redirect:home.html";
//    }

    @RequestMapping("/home")
    public String hello(){
        System.out.println("hello security");
        return "redirect:home.html";
    }

}