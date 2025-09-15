package com.contractEmployee.contractEmployee.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key accessKey;
    private final Key refreshKey;

    private final long accessTtlMs;   // default: 1 hour
    private final long refreshTtlMs;  // default: 7 days

    public JwtUtil(
            @Value("${security.jwt.access-secret}") String accessSecret,
            @Value("${security.jwt.refresh-secret}") String refreshSecret,
            @Value("${security.jwt.access-ttl-ms:3600000}") long accessTtlMs,
            @Value("${security.jwt.refresh-ttl-ms:604800000}") long refreshTtlMs
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlMs = accessTtlMs;
        this.refreshTtlMs = refreshTtlMs;
    }

    // ===== Token Generation =====
    public String generateAccessToken(String username, String role) {
        return buildToken(username, role, accessTtlMs, accessKey);
    }

    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTtlMs)))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String buildToken(String username, String role, long ttl, Key key) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(Map.of("role", role))
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(ttl)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ===== Token Validation =====
    public boolean validateAccessToken(String token) {
        return validate(token, accessKey);
    }

    public boolean validateRefreshToken(String token) {
        return validate(token, refreshKey);
    }

    private boolean validate(String token, Key key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ===== Extract Information =====
    public String extractUsernameFromAccess(String token) {
        return extractSubject(token, accessKey);
    }

    public String extractUsernameFromRefresh(String token) {
        return extractSubject(token, refreshKey);
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    private String extractSubject(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
