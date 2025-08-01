package com.example.DongNaeJoGak.domain.auth.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.auth.service.AuthService;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.domain.idea.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.GoogleOAuth2ConfigData;
import com.example.DongNaeJoGak.global.security.data.NaverOAuth2ConfigData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final NaverOAuth2ConfigData naverOAuth2ConfigData;
    private final GoogleOAuth2ConfigData googleOAuth2ConfigData;

    @GetMapping("/oauth2/authorize/naver")
    public void redirectToNaver(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String state = generateState(); // 랜덤 문자열 생성
        request.getSession().setAttribute("state", state); // 세션에 저장


        String redirectUri = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + naverOAuth2ConfigData.getClientId()
                + "&redirect_uri=" + naverOAuth2ConfigData.getRedirectUri()
                + "&state=" + state
                ;

        response.sendRedirect(redirectUri); // 네이버 로그인 페이지로 이동
    }

    @GetMapping("/login/oauth2/code/naver")
    public void naverCallback(@RequestParam("code") String code,
                         @RequestParam("state") String state,
                         HttpServletResponse response) {

        redirectToFrontEnd("naver", code, state, response);

    }

    @GetMapping("/oauth2/authorize/google")
    public void redirectToGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // (선택) state 생성
        String state = generateState();
        request.getSession().setAttribute("state", state);

        // Google 로그인 URL 생성
        String redirectUri = "https://accounts.google.com/o/oauth2/auth"
                + "?response_type=code"
                + "&client_id=" + googleOAuth2ConfigData.getClientId()
                + "&redirect_uri=" + googleOAuth2ConfigData.getRedirectUri()
                + "&scope=" + URLEncoder.encode(googleOAuth2ConfigData.getScopes(), StandardCharsets.UTF_8)
                + "&access_type=offline"
                + "&prompt=consent"
                + "&state=" + state;

        response.sendRedirect(redirectUri);
    }


    @GetMapping("/login/oauth2/code/google")
    public void googleCallback(@RequestParam("code") String code,
                         @RequestParam("state") String state,
                         HttpServletResponse response) {

        redirectToFrontEnd("google", code, state, response);

    }


    @GetMapping("/api/oauth2/login/{providerType}")
    public ApiResponse<OAuthResponseDTO.LoginResponse> login(@PathVariable ProviderType providerType,
                                                             @RequestParam("code") String code,
                                                             @RequestParam("state") String state) {
        OAuthResponseDTO.LoginResponse response = authService.login(providerType, code, state);

        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/api/oauth2/reissue")
    public ApiResponse<OAuthResponseDTO.RefreshTokenResponse> reissueToken(@RequestBody OAuthRequestDTO.RefreshTokenRequest request) {

        OAuthResponseDTO.RefreshTokenResponse response = authService.reissueToken(request);
        return ApiResponse.onSuccess(response);
    }


    @PostMapping("/api/oauth2/logout")
    public ApiResponse<String> logout(@AuthenticatedMember Member member) {
        return  ApiResponse.onSuccess("로그아웃에 성공했습니다");
    }



    private void redirectToFrontEnd(String providerType, String code, String state, HttpServletResponse response) {
        System.out.println("code: " + code);
        System.out.println("state: " + state);

        try {
            String frontendUrl = "http://localhost:5173/login/" + providerType + "/success"
                    + "?code=" + code + "&state=" + state;

            response.sendRedirect(frontendUrl);

        } catch (IOException e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_REDIRECTION);
        }

    }

    public String generateState() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(80, secureRandom).toString(32);
    }
}
