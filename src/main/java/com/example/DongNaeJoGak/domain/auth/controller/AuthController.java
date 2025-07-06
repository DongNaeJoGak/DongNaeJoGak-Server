package com.example.DongNaeJoGak.domain.auth.controller;

import com.example.DongNaeJoGak.domain.auth.dto.response.NaverOAuth2DTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.auth.service.AuthService;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.NaverOAuth2ConfigData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
@Tag(name = "OAuth2 인증 API", description = "네이버 OAuth2 로그인 플로우 API")
public class AuthController {

    private final AuthService authService;
    private final NaverOAuth2ConfigData naverOAuth2ConfigData;

    @Operation(
            summary = "네이버 로그인 페이지로 리다이렉트",
            description = """
                네이버 OAuth2 로그인을 시작하기 위해 네이버 로그인 페이지로 리다이렉트합니다.
                내부적으로 state 값을 세션에 저장하고, 네이버에 리다이렉트 URL로 전달합니다.
                """
    )
    @GetMapping("/oauth2/authorize/naver")
    public void redirectToNaver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String state = generateState(); // 랜덤 문자열 생성
        request.getSession().setAttribute("state", state); // 세션에 저장

        String redirectUri = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + naverOAuth2ConfigData.getClientId()
                + "&redirect_uri=http://dongnaejogak.kro.kr/login/oauth2/code/naver"
                + "&state=" + state;

        response.sendRedirect(redirectUri); // 네이버 로그인 페이지로 이동
    }

    @Operation(
            summary = "네이버 OAuth2 콜백",
            description = """
                네이버 로그인 완료 후 Redirect URI로 전달된 code/state를 받아 검증합니다.
                state 값이 세션에 저장된 state와 다르면 예외를 던집니다.
                """
    )
    @GetMapping("/login/oauth2/code/naver")
    public ResponseEntity<?> callback(
            @Parameter(description = "네이버에서 전달한 인가 코드", example = "abc123")
            @RequestParam("code") String code,

            @Parameter(description = "CSRF 방지용 state 파라미터", example = "xyz987")
            @RequestParam("state") String state,

            HttpServletRequest request) {

        System.out.println("code: " + code);
        System.out.println("state: " + state);

        String sessionState = (String) request.getSession().getAttribute("state");

        if (sessionState == null || !sessionState.equals(state)) {
            throw new OAuth2Exception(OAuth2ErrorStatus.INVALID_STATE);
        }

        NaverOAuth2DTO.NaverTokenResponse response = new NaverOAuth2DTO.NaverTokenResponse(code, state);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "OAuth2 로그인 처리",
            description = """
                인가 코드(code)와 state를 받아 OAuth2 인증 후 토큰을 발급합니다.
                providerType 경로 변수는 NAVER, GOOGLE 등 지원하는 소셜 타입을 의미합니다.
                """
    )
    @GetMapping("/api/oauth2/login/{providerType}")
    public ApiResponse<OAuthResponseDTO.LoginResponse> login(
            @Parameter(description = "소셜 로그인 제공자 타입", example = "NAVER")
            @PathVariable ProviderType providerType,

            @Parameter(description = "인가 코드", example = "abc123")
            @RequestParam("code") String code,

            @Parameter(description = "CSRF 방지용 state", example = "xyz987")
            @RequestParam("state") String state) {

        OAuthResponseDTO.LoginResponse response = authService.login(providerType, code, state);
        return ApiResponse.onSuccess(response);
    }

    public String generateState() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString(32);
    }
}
