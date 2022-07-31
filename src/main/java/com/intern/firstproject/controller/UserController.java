package com.intern.firstproject.controller;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.service.JwtService;
import com.intern.firstproject.service.UserService;
import com.intern.firstproject.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/user")
@SuppressWarnings({"unchecked","rawtypes"})
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private LogUtils logUtils=LogUtils.getInstance();

    /***
     * register user
     * @param userProfile
     * @return
     */
    @PostMapping("/register")
    public JsonResult register(@RequestBody UserProfile userProfile){
        log.info("register << (User : "+logUtils.printObjectAsLog(userProfile)+")");
        JsonResult response= userService.insertUser(userProfile);
        log.info("register >> (Json : "+logUtils.printObjectAsLog(response)+")");
        return response;
    }


    @PostMapping("/login")
    public JsonResult loginAndGetToken(@RequestBody UserProfile userProfile){
        log.info("loginAndGetToken << (User : "+logUtils.printObjectAsLog(userProfile)+")");
        JsonResult response= userService.login(userProfile);
        log.info("loginAndGetToken >> (Json : "+logUtils.printObjectAsLog(response)+")");
        return response;
    }

    @GetMapping("/logout")
    public JsonResult logout(@RequestHeader  Map<String, String> request) {
        log.info("logout << (Map<String ,String> : "+logUtils.printObjectAsLog(request)+")");
        JsonResult response= userService.logout(request);
        log.info("logout >> (Json : "+logUtils.printObjectAsLog(response)+")");
        return response;

    }

    @GetMapping("/deleteUser")
    public JsonResult  deleteUser(){
        log.info("deleteUser << (void)");
        JsonResult response=userService.deleteUser();
        log.info("deleteUser >> (Json :"+logUtils.printObjectAsLog(response)+")");
        return response;
    }

    @PostMapping("/insertEmail")
    public JsonResult  insertEmail(String email){
        log.info("insertEmail << (String : "+email+")");
        JsonResult response=userService.insertEmail(email);
        log.info("insertEmail >> (Json :"+logUtils.printObjectAsLog(response)+")");
        return response;
    }

    @PostMapping("/setAuthority")
    public JsonResult setAuthority(String username,String authority){
        log.info("setAuthority << (String : "+username+")");
        JsonResult response=userService.setAuthority(username,authority);
        log.info("setAuthority >> (Json : "+logUtils.printObjectAsLog(response)+")");
        return response;
    }
}
