package com.example.DongNaeJoGak.domain.auth.dto.response;

import lombok.Getter;

public class GoogleOAuth2DTO {

    @Getter
    public static class OAuth2TokenDTO {
        private String accessToken;
        private String expiresIn;
        private String refreshToken;
        private String scope;
        private String tokenType;   // 반환된 토큰 유형(Bearer 고정)
        private String idToken;
    }

    @Getter
    public static class GoogleInfoResponse {
        private String iss;
        private String azp;
        private String aud;
        private String sub;                 // 구글 사용자 id
        private String email;
        private String email_verified;
        private String at_hash;
        private String name;
        private String picture;
        private String given_name;
        private String family_name;
        private String locale;
        private String iat;
        private String exp;
        private String alg;
        private String kid;
        private String typ;
    }
}
