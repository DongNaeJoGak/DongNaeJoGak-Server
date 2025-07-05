package com.example.DongNaeJoGak.domain.auth.controller;

import com.example.DongNaeJoGak.domain.auth.dto.response.NaverOAuth2DTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.auth.service.AuthService;
import com.example.DongNaeJoGak.domain.auth.service.NaverOAuth2Service;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.NaverOAuth2ConfigData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class AuthController {

    private final AuthService authService;
    private final NaverOAuth2Service naverOAuth2Service;
    private final NaverOAuth2ConfigData naverOAuth2ConfigData;

    @GetMapping("/oauth2/authorize/naver")
    public void redirectToNaver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String state = generateState(); // 랜덤 문자열 생성
        request.getSession().setAttribute("state", state); // 세션에 저장

        String redirectUri = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + naverOAuth2ConfigData.getClientId()
                + "&redirect_uri=http://localhost:8080/login/oauth2/code/naver"
                + "&state=" + state;

        response.sendRedirect(redirectUri); // 네이버 로그인 페이지로 이동
    }


    @GetMapping("/login/oauth2/code/naver")
    public ResponseEntity<?> callback(@RequestParam("code") String code,
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


    @GetMapping("/api/oauth2/login/{providerType}")
    public ApiResponse<OAuthResponseDTO.LoginResponse> login(@PathVariable ProviderType providerType,
                                                             @RequestParam("code") String code,
                                                             @RequestParam("state") String state) {
        OAuthResponseDTO.LoginResponse response = authService.login(providerType, code, state);

        return ApiResponse.onSuccess(response);
    }


    public String generateState() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString(32);
    }

}
