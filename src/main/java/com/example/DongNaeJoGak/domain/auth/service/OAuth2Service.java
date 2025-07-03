package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;

public interface OAuth2Service {

    OAuthResponseDTO.LoginResponse login(String code);
}
