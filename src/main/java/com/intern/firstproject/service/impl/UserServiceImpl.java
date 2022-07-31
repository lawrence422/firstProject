package com.intern.firstproject.service.impl;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.mapper.UserProfileMapper;
import com.intern.firstproject.service.JwtService;
import com.intern.firstproject.service.UserService;
import com.intern.firstproject.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    StringUtils stringUtils = StringUtils.getInstance();

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public int insertUser(UserProfile userProfile) {
        if (userProfile ==null||stringUtils.isAnyEmpty(userProfile.getUsername(), userProfile.getPassword())) {
            return -1;
        }else if (userProfileMapper.checkUsernameExist(userProfile.getUsername())==1){
            return -2;
        }

        int temp= userProfileMapper.insertUser(userProfile.getUsername(),userProfile.getPassword());

        if (temp!=1){
            return -3;
        }else{
            return 1;
        }
    }

    @Override
    public JsonResult<Map<String, String>> login(UserProfile userProfile) {
        String token= jwtService.generateToken(userProfile);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), userProfile.getPassword());
        authenticationManager.authenticate(authentication);
        Map<String,String> map= Collections.singletonMap("token",token);
        return JsonResult.success(map);
    }

    @Override
    public JsonResult<Map<String, String>> deleteUser() {
        return null;
    }
}
