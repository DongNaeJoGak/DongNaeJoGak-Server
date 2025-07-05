package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;

public interface OAuth2Service {

    OAuthResponseDTO.LoginResponse login(ProviderType providerType, String code, String state);
}
