package com.example.DongNaeJoGak.domain.auth.controller;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.auth.service.AuthService;
import com.example.DongNaeJoGak.domain.auth.service.NaverOAuth2Service;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final NaverOAuth2Service naverOAuth2Service;

    @GetMapping("/login/oauth2/code/naver")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {

        String accessToken = naverOAuth2Service.getAccessToken(code);
        return ResponseEntity.ok(accessToken);
    }


    @GetMapping("/api/oauth2/login/{providerType}")
    public ApiResponse<OAuthResponseDTO.LoginResponse> login(@PathVariable String providerType,
                                                             @RequestParam("code") String code) {
        OAuthResponseDTO.LoginResponse response = authService.login(providerType, code);

        return ApiResponse.onSuccess(response);
    }
}
