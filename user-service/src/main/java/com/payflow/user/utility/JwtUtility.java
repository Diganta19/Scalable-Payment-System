package com.payflow.user.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtility {

     @Value("${jwt.secret:your_super_secret_key_that_is_at_least_256_bits_long_1234567890}")
    private  String SECRET_KEY;

     @Value("${jwt.access-token-expiration:900000}")
    private long EXPIRATION_TIME;


    private  SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Long userId, String email){
        return Jwts.builder()
        .setSubject(email)
        .claim("userId", userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String getEmailFromToken(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }
    public Long getUserIdFromToken(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get("userId",Long.class);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
