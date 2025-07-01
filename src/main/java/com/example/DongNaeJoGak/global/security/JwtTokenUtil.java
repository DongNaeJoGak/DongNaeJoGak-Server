package com.example.DongNaeJoGak.global.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;

@Component
public class JwtTokenUtil {

    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;

    @Value("${jwt.secret}")
    private String key;
    private SecretKey secretKey;

    public String createAccessTOken(Long userId) {

    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }



}
