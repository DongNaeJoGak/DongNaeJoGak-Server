package com.example.DongNaeJoGak.global.security.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
// application.yml 에서 jwt 관련 설정값을 읽어오는 클래스
public class JwtConfigData {
    private String secret;
    private JwtTime time;


    @Getter
    @Setter
    public static class JwtTime {
        private long accessToken;
        private long refreshToken;
    }
}
