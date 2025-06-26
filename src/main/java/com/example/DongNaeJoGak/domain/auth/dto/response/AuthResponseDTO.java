package com.example.DongNaeJoGak.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

public class AuthResponseDTO {

    @Getter
    @Builder
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private LoginUserInfo userInfo;

        @Getter
        @Builder
        public static class LoginUserInfo {
            private Long userId;
            private String username;
            private String image;
        }
    }

    @Getter
    @Builder
    public static class RefreshTokenResponse {
        private String accessToken;
    }
}
