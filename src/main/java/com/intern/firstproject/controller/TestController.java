package com.intern.firstproject.controller;

import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth")
    public ResponseEntity<Map<String,String>> testToken(@RequestBody UserProfile userProfile){
        String token= jwtService.generateToken(userProfile);
        Map<String,String> response= Collections.singletonMap("token",token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);
        if (response==null){
            response=new HashMap<>();
            response.put("The String ResponseBody with custom status code (403 Forbidden)", HttpStatus.FORBIDDEN);
            return new ResponseEntity(response,HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(response);
    }


}
