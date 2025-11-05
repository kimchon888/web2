package com.example.coffee.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    // Khóa bí mật cố định
    private static final String SECRET_KEY = "mysecretkey1234567890abcdefmysecretkey";

    //  Sinh token có roles
    public String generateToken(String username, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 ngày
                .addClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //  Kiểm tra token hợp lệ
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (username.equals(extractedUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    //  Lấy username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //  Lấy roles từ token
    public Set<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List<?>) {
            List<?> list = (List<?>) rolesObj;
            Set<String> roleSet = new HashSet<>();
            for (Object r : list) roleSet.add(String.valueOf(r));
            return roleSet;
        }
        return Collections.emptySet();
    }

    //  Kiểm tra token hết hạn
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    //  Giải mã token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //  Tạo Key cố định từ SECRET_KEY
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
