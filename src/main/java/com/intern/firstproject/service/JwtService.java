package com.intern.firstproject.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    Boolean isTokenExpire(String token);

    Claims getClaimsFromToken(String token);

    Map<String, Object> parseToken(String token);

    String getUsername(String token);
}
