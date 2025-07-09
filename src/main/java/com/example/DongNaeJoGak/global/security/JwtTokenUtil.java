package com.example.DongNaeJoGak.global.security;

import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.TokenErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.TokenException;
import com.example.DongNaeJoGak.global.security.data.JwtConfigData;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;
    private final Duration refreshExpiration;

    public JwtTokenUtil(JwtConfigData jwtConfigData) {
        this.secretKey = Keys.hmacShaKeyFor(jwtConfigData.getSecret().getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = Duration.ofMillis(jwtConfigData.getTime().getAccess());
        this.refreshExpiration = Duration.ofMillis(jwtConfigData.getTime().getRefresh());
    }

    // 토큰 생성
    public String createAccessToken(Member member) {
        return createToken(member, accessExpiration);
    }

    public String createRefreshToken(Member member) {
        return createToken(member, refreshExpiration);
    }

    private String createToken(Member member, Duration expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(member.getEmail())
                .claim("id", member.getId())                // 클레임에 id 넣기
                .issuedAt(Date.from(now))                       // 발급 시간
                .expiration(Date.from(now.plus(expiration)))    // 만료 시간
                .signWith(secretKey)                            // 시크릿 키로 서명
                .compact();                                     // 토큰 생성
    }

    // 토큰에서 userId 뽑기
    public Long getUserIdFromToken(String token) {
        try {
            return getClaims(token).getPayload().get("id", Long.class);
        } catch (JwtException e) {
            return null;
        }
    }

    // 토큰 유효한지 확인
    public boolean isValidateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenException(TokenErrorStatus.TOKEN_EXPIRED);
        } catch (JwtException e) {
            return false;
        }
    }
    // 토큰 검징 및 파싱
    private Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)              // 시크릿 키로 서명 검증
                .clockSkewSeconds(60)            // 60초 정도 시간 오차 허용
                .build()
                .parseSignedClaims(token);          // 서명 검증 후 파싱
    }


}
