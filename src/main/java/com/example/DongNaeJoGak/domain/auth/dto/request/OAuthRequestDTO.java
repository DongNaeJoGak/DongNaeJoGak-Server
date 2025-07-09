package com.example.DongNaeJoGak.domain.auth.dto.request;

import com.example.DongNaeJoGak.domain.idea.member.entity.enums.ProviderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OAuthRequestDTO {

    @Getter
    @Builder
    @Setter
    public static class LoginRequest {
        private ProviderType providerType;
        private String providerId;
        private String username;
        private String email;
        private String image;
    }

    @Getter
    public static class RefreshTokenRequest {
        private String refreshToken;
    }
}
