package com.example.DongNaeJoGak.domain.auth.dto.response;


import lombok.Getter;

public class NaverOAuth2DTO {

    @Getter
    public static class OAuth2TokenDTO {
        String token_type;
        String access_token;
        String refresh_token;
        Long expires_in;
        Long refresh_token_expires_in;
    }

    @Getter
    public static class NaverProfile {
        private Long id;
        private String connected_at;
        private NaverProperties properties;
        private NaverAccount naver_account;

        @Getter
        public static class NaverProperties {
            private String nickname;
            private String profile_image;
            private String thumbnail_image;
        }

        @Getter
        public static class NaverAccount {
            private String email;
            private Boolean is_email_verified;
            private Boolean email_needs_agreement;
            private Boolean has_email;
            private Boolean profile_nickname_needs_agreement;
            private Boolean profile_image_needs_agreement;
            private Boolean email_needs_argument;
            private Boolean is_email_valid;
            private Profile profile;

            @Getter
            public static class Profile {
                private String nickname;
                private String thumbnail_image_url;
                private String profile_image_url;
                private Boolean is_default_nickname;
                private Boolean is_default_image;
            }
        }
    }
}
