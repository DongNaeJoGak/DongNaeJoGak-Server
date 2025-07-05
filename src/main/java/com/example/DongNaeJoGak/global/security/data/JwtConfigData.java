package com.example.DongNaeJoGak.global.security.data;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
// application.yml 에서 jwt 관련 설정값을 읽어오는 클래스
public class JwtConfigData {

    private String secret;
    private JwtTime time = new JwtTime();  // ✅ 명시적 초기화


    @Getter
    @Setter
    public static class JwtTime {
        private long access;
        private long refresh;
    }

    @PostConstruct
    public void init() {
        System.out.println("JWT Config Loaded: " + secret + ", access = " + time.getAccess() + ", refresh = " + time.getRefresh());
    }


}
