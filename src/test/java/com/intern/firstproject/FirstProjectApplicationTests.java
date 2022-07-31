package com.intern.firstproject;

import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.mapper.UserProfileMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.annotation.Resource;

@SpringBootTest
class FirstProjectApplicationTests {

    @Resource
    private UserProfileMapper userProfileMapper;

    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Test
    public void testMapper() {
        UserProfile userProfileTest = new UserProfile();
        userProfileTest.setUserEmail("lawrence4221@gmail.com");
        userProfileTest.setUsername("ql0w0l4");
        userProfileTest.setPassword("1234");
        int temp = userProfileMapper.insertUser(userProfileTest.getUsername(), passwordEncoder.encode(userProfileTest.getPassword()));
        Assertions.assertEquals(1, temp);
        String str = userProfileMapper.getPassword(userProfileTest.getUsername());
        Assertions.assertEquals(true, passwordEncoder.matches(userProfileTest.getPassword(), str));

        String authority= userProfileMapper.getAuthority("ql0w0l4");
        Assertions.assertEquals("normal",authority);
        userProfileMapper.deleteUser("ql0w0l4");


    }

}
