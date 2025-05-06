package com.example.mxh.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    private static String secret_key = "3c8aab25a0d8f9dc7a3b0fcda59fd3532421c044b135b790eb75b38e46f2bb5cb2b5e4565e8b9913f9721cd2ac7d62cc2e1b9b1379075aa41f30c21e48c6458ff2e497bb1c085cab7ea36ea3846aee5ed47cb54180411e6f858e58f59b1535f502851b4f897de69f37f9109fd6dc21a336defd7895c583651d93c5e5277df3bfb02671cfb7598d0ab2025f56ea16804e655d5db2fdbfdda1fcdff761e76b42d5ba4dde14500eeedeb50db55ae1292c47e9a9f88f04eaadf84bac57129e228bca2415e1c0c66d41aa514b2a4dee64fc4b4007fc8447c22f7090fb46fdd83888a93c5eea86727d158d3b60ad2d260502950d4ccbad50999a23a99f290746ad1767";

    private static SecretKey key = Keys.hmacShaKeyFor(secret_key.getBytes());
    public static String generateToken(Authentication auth){
        String jwt  = Jwts.builder().setIssuer("mxh")
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000))
                .claim("username",auth.getName())
                .signWith(key).compact();
        return jwt;
    }
    public  static String getUsernameFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims  claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt)
                .getBody();
        String username = String.valueOf(claims.get("username"));
        return username;
    }
}