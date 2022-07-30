package com.intern.firstproject.service.impl;

import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.mapper.UserProfileMapper;
import com.intern.firstproject.service.UserService;
import com.intern.firstproject.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    StringUtils stringUtils = StringUtils.getInstance();

    @Resource
    private UserProfileMapper userProfileMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int insertUser(UserProfile userProfile) {
        if (userProfile ==null||stringUtils.isAnyEmpty(userProfile.getUsername(), userProfile.getPassword())) {
            return -1;
        }else if (userProfileMapper.checkUsernameExist(userProfile.getUsername())==1){
            return -2;
        }

        int temp= userProfileMapper.insertUser(userProfile.getUsername(),passwordEncoder.encode(userProfile.getPassword()));

        if (temp!=1){
            return -3;
        }else{
            return 1;
        }
    }
}
