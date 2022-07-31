package com.intern.firstproject.service;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;

import java.util.Map;


public interface UserService {
    int insertUser(UserProfile userProfile);

    JsonResult<Map<String, String>> login(UserProfile userProfile);

    JsonResult<Map<String, String>> deleteUser();
}
