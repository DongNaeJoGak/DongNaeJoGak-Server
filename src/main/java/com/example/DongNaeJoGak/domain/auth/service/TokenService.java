package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;

public interface TokenService {
    OAuthResponseDTO.LoginResponse createLoginToken(Member member);

    void deleteToken(Member member);

}
