package com.example.DongNaeJoGak.domain.auth.dto.response;


import lombok.Getter;

public class NaverOAuth2DTO {

    @Getter
    public static class OAuth2TokenDTO {
        String access_token;
        String refresh_token;
        String token_type;
        Long expires_in;
    }

    @Getter
    public static class NaverProfile {
        private String resultcode;
        private String message;
        private NaverResponse response;

        @Getter
        public static class NaverResponse {
            private String id;
            private String nickname;
            private String email;
            private String name;
            private String profile_image;
            private String age;
            private String gender;
            private String birthday;
            private String birthyear;
            private String mobile;
        }

    }
}
