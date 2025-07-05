package com.example.DongNaeJoGak.domain.auth.dto.response;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

public class OAuthResponseDTO {

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

        public static LoginResponse toLoginResponse(String accessToken, String refreshToken) {
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        public static Member toMember(OAuthRequestDTO.LoginRequest request) {
            return Member.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .profileImage(request.getImage())
                    .providerId(request.getProviderId())
                    .providerType(request.getProviderType())
                    .build();
        }
    }



    @Getter
    @Builder
    public static class RefreshTokenResponse {
        private String accessToken;
    }
}
