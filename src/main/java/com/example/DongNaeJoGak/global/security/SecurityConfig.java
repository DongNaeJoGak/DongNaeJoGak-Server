package com.example.DongNaeJoGak.global.security;

import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.domain.member.service.MemberService;
import com.example.DongNaeJoGak.global.security.filter.JwtTokenExceptionFilter;
import com.example.DongNaeJoGak.global.security.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private final MemberService memberService;

    private final String[] allowedUrls = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/oauth2/**",
            "/login/oauth2/code/naver",
            "/oauth2/authorize/naver",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic((AbstractHttpConfigurer::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowedUrls).permitAll()            // 위에 정의한 allowedUrls는 인증 필요 없음
                        .anyRequest().authenticated()                        // 그 외는 인증 필요
                )
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)  // 먼저 인증 필터
                .addFilterBefore(jwtTokenExceptionFilter(), JwtTokenFilter.class)  // 그 다음 예외 필터


        ;

        return http.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenUtil, securityContextRepository(), memberService);
    }

    @Bean
    public JwtTokenExceptionFilter jwtTokenExceptionFilter() {
        return new JwtTokenExceptionFilter();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new RequestAttributeSecurityContextRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

