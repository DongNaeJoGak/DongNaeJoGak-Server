package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.global.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public OAuthResponseDTO.LoginResponse createLoginToken(Member member) {
        return OAuthResponseDTO.LoginResponse.toLoginResponse(
                jwtTokenUtil.createAccessToken(member), jwtTokenUtil.createRefreshToken(member));
    }

    @Override
    public void deleteToken(Member member) {

    }
}
