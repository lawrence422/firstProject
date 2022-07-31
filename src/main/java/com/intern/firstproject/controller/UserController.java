package com.intern.firstproject.controller;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.service.JwtService;
import com.intern.firstproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public JsonResult<Map<String,String>> loginAndGetToken(@RequestBody UserProfile userProfile){
        JsonResult<Map<String,String>> response= userService.login(userProfile);
//        String token= jwtService.generateToken(userProfile);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), userProfile.getPassword());
//        authenticationManager.authenticate(authentication);
//        Map<String,String> map= Collections.singletonMap("token",token);

        return response;
    }

    @PostMapping("/parse")
    public JsonResult<Map<String,Object>> parse(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);
        return JsonResult.success(response);
    }

    @GetMapping("/logout")
    public JsonResult<Map<String,String>> logout(@RequestHeader  Map<String, String> request) {
        SecurityContextHolder.clearContext();
        return JsonResult.success(request);
    }

    @GetMapping("/deleteUser")
    public JsonResult <Map<String,String>> deleteUser(){
        JsonResult<Map<String,String>> response=userService.deleteUser();
        return response;
    }
}
