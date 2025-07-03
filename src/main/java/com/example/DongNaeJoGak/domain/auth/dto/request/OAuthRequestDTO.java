package com.example.DongNaeJoGak.domain.auth.dto.request;

import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import lombok.Builder;
import lombok.Getter;

public class OAuthRequestDTO {

    @Getter
    @Builder
    public static class LoginRequest {
        private ProviderType providerType;
        private String providerId;
        private String username;
        private String image;
    }

    @Getter
    public static class RefreshTokenRequest {
        private String refreshToken;
    }
}
