package com.intern.firstproject;

import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.mapper.UserProfileMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.annotation.Resource;

@SpringBootTest
class FirstProjectApplicationTests {

    @Resource
    private UserProfileMapper userProfileMapper;
    @Resource
    PasswordEncoder passwordEncoder;

    @Test
    public void testMapper() {
        UserProfile userProfileTest = new UserProfile();
        userProfileTest.setUserEmail("lawrence422@gmail.com");
        userProfileTest.setUsername("ql0w0l");
        userProfileTest.setPassword("123");
        int temp = userProfileMapper.insertUser(userProfileTest.getUsername(), passwordEncoder.encode(userProfileTest.getPassword()));
        Assertions.assertEquals(1, temp);
        String str = userProfileMapper.getPassword(userProfileTest.getUsername());
        Assertions.assertEquals(true, passwordEncoder.matches(userProfileTest.getPassword(), str));

//        int temp2 = userProfileMapper.deleteUser(userProfileTest.getUserEmail());
//        Assertions.assertEquals(1, temp2);
    }

}
