package com.example.DongNaeJoGak.global.config;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMemberResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
// 만든 AuthenticatedMemberResolver를 사용하도록 설정
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticatedMemberResolver authenticatedMemberResolver;

    public WebConfig(AuthenticatedMemberResolver authenticatedMemberResolver) {
        this.authenticatedMemberResolver = authenticatedMemberResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedMemberResolver);
    }
}
