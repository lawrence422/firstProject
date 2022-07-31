package com.intern.firstproject.service.impl;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.mapper.UserProfileMapper;
import com.intern.firstproject.service.JwtService;
import com.intern.firstproject.service.UserService;
import com.intern.firstproject.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@SuppressWarnings({"rawtypes"})
public class UserServiceImpl implements UserService {
    private StringUtils stringUtils = StringUtils.getInstance();

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public JsonResult insertUser(UserProfile userProfile) {
        if (stringUtils.isAnyEmpty(userProfile.getUsername(), userProfile.getPassword())) {
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Empty input error");
        }else if (userProfileMapper.checkUsernameExist(userProfile.getUsername())==1){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Username have been exist");
        }

       int temp= userProfileMapper.insertUser(userProfile.getUsername(),passwordEncoder.encode(userProfile.getPassword()));


        if (temp!=1){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL insert error");
        }
        return JsonResult.success("");
    }

    @Override
    public JsonResult login(UserProfile userProfile) {
        String token= jwtService.generateToken(userProfile);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), userProfile.getPassword());
        authenticationManager.authenticate(authentication);
        Map<String,String> map= Collections.singletonMap("token",token);
        return JsonResult.success(map);
    }

    @Override
    public JsonResult deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        int temp= userProfileMapper.deleteUser(authentication.getName());
        if (temp==0){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL delete error");
        }
        SecurityContextHolder.clearContext();
        return JsonResult.success("");
    }

    @Override
    public JsonResult logout(Map<String, String> request) {
        SecurityContextHolder.clearContext();
        return JsonResult.success("");
    }


    @Override
    public JsonResult insertEmail(String userEmail) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        int temp=userProfileMapper.updatetEmail(authentication.getName(),userEmail);
        if (temp==0){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL insert error");
        }
        return JsonResult.success("");
    }

    @Override
    public JsonResult setAuthority(String name, String authority) {
        if (stringUtils.isAnyEmpty(name,authority)){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Empty input error");
        }
        int temp=userProfileMapper.updateAuthority(name,authority);
        if (temp==0){
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL insert error");
        }
        return JsonResult.success("");
    }
}
