package com.example.demo.secruity;

import java.util.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Change this to a secure key
    private long validity = 5 * 60 * 1000; // 5 minutes



    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token= createToken(claims, username);

        return token;
    }

    public String generateToken(UserDetails userDetails) {


        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 *500 * 60 * 10))) // 10 hours validity
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        if (token == null || token.isEmpty()) {
            return false; // Token is null or empty
        }
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();

        // Test user details
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());

        // Generate token
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("Generated Token: " + token);

        // Extract username
        String username = jwtUtil.extractUsername(token);
        System.out.println("Extracted Username: " + username);

        // Validate token
        boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());
        System.out.println("Token is Valid: " + isValid);
    }
}