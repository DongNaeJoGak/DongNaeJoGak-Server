package com.example.DongNaeJoGak.domain.auth.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.NaverOAuth2DTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.auth.service.AuthService;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.NaverOAuth2ConfigData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final NaverOAuth2ConfigData naverOAuth2ConfigData;

    @GetMapping("/oauth2/authorize/naver")
    public void redirectToNaver(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String redirectUri = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + naverOAuth2ConfigData.getClientId()
                + "&redirect_uri=" + naverOAuth2ConfigData.getRedirectUri()
                ;
      
        response.sendRedirect(redirectUri); // 네이버 로그인 페이지로 이동
    }



    @GetMapping("/login/oauth2/code/naver")
    public void callback(@RequestParam("code") String code,
                                      @RequestParam("state") String state,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        System.out.println("code: " + code);
        System.out.println("state: " + state);

        try {
            String sessionState = (String) request.getSession().getAttribute("state");

            if (sessionState == null || !sessionState.equals(state)) {
                throw new OAuth2Exception(OAuth2ErrorStatus.INVALID_STATE);
            }

            String frontendUrl = "http://localhost:3000/login/oauth2/code/naver"
                    + "?code=" + code + "&state=" + state;

            response.sendRedirect(frontendUrl);

        } catch (IOException e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_REDIRECTION);
        }


//        NaverOAuth2DTO.NaverTokenResponse response = new NaverOAuth2DTO.NaverTokenResponse(code, state);
//        return ResponseEntity.ok(response);
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

}
