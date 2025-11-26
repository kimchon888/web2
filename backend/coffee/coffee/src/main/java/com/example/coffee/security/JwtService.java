package com.example.coffee.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    // ✅ Khóa bí mật Base64 đủ độ dài (32 bytes+)
    private static final String SECRET_KEY =
            "a2F3bHNkamZsajM0N2xqZmdoc2prZmdqa2ZoZ2prZmdoamZna2Zq";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }

    // ✅ Sinh token kèm roles
    public String generateToken(String username, Set<String> roles) {

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 ngày
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Kiểm tra token hợp lệ
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Lấy username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Lấy roles từ claim
    public Set<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<?> roles = claims.get("roles", List.class);
        if (roles == null) return Collections.emptySet();
        Set<String> result = new HashSet<>();
        roles.forEach(r -> result.add(String.valueOf(r)));
        return result;
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
