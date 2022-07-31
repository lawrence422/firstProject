package com.intern.firstproject.controller;

import com.intern.firstproject.dao.pojo.JsonResult;
import com.intern.firstproject.dao.pojo.UserProfile;
import com.intern.firstproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody UserProfile userProfile) {
        String token = jwtService.generateToken(userProfile);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), userProfile.getPassword());
        authenticationManager.authenticate(authentication);
        Map<String, String> response = Collections.singletonMap("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public JsonResult<Map<String, Object>> parse(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);
        return JsonResult.success(response);
    }

    @GetMapping("/test")
    public String test() {
        return "test success";
    }


}
