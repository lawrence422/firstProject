package com.intern.firstproject.service;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;

import java.util.Map;

@SuppressWarnings({"rawtypes"})
public interface UserService {
    JsonResult insertUser(UserProfile userProfile);

    JsonResult login(UserProfile userProfile);

    JsonResult deleteUser();

    JsonResult logout(Map<String, String> request);

    JsonResult insertEmail(String email);

    JsonResult setAuthority(String name, String authority);
}
