package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;

public interface AuthService {

    OAuthResponseDTO.LoginResponse login(String providerType, String code);
}
