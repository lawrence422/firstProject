package com.intern.firstproject.service.impl;

import com.intern.firstproject.service.JwtService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private AuthenticationManager authenticationManager;


    private static String SECRET_KEY = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIAVbb8MMmDDA5xKw1L6FbEquZ4RjZOLqe4aroh3+abXQ ed25519-key-20220729";


    private static long EXPIRATION_TIME = 600;

    @Override
    public String generateToken(UserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        authenticationManager.authenticate(authentication);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = getClaimsFromToken(token);
        User user = (User) userDetails;
        String username = getClaimsFromToken(token).getSubject();
        return username.equals(user.getUsername()) && !isTokenExpire(token);
    }

    @Override
    public Boolean isTokenExpire(String token) {

        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }

    }


    public Map<String, Object> parseToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return null;
    }
}
