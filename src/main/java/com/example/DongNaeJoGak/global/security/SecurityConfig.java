package com.example.DongNaeJoGak.global.security;

import com.example.DongNaeJoGak.domain.idea.member.service.MemberService;
import com.example.DongNaeJoGak.global.security.filter.JwtTokenExceptionFilter;
import com.example.DongNaeJoGak.global.security.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
            "/api/oauth2/login/**",
            "/login/**",
            "/oauth2/authorize/**",
            "/api/ideas/**",                 // 아이디어 관련 CR 은 접근 가능
            "/api/ideas/{ideaId}/comments/list"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // 추가해야 CORS 설정이 적용됨
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic((AbstractHttpConfigurer::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
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
        config.setAllowedOrigins(List.of(
                "http://localhost:5173", // 프론트 dev 주소
                "http://localhost:3000", // 다른 로컬 주소 (필요 시)
                "http://dongnaejogak.kro.kr", // 추후 배포용 (필요 시)
                "https://dongnaejogak.kro.kr"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

