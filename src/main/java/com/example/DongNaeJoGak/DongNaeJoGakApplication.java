package com.example.DongNaeJoGak;

import com.example.DongNaeJoGak.global.security.data.JwtConfigData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(JwtConfigData.class)
public class DongNaeJoGakApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongNaeJoGakApplication.class, args);
    }

}
