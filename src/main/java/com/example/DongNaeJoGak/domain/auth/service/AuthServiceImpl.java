package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuth2Service oAuth2Service;

    private final Map<String, OAuth2Service> oAuth2ServiceMap;

    @Override
    public OAuthResponseDTO.LoginResponse login(String providerType, String code) {
        OAuth2Service service = oAuth2ServiceMap.get(providerType.toLowerCase());

        if (service == null) {
            throw new OAuth2Exception(OAuth2ErrorStatus.UNSUPPORTED_OAUTH2_PROVIDER);
        }

        return service.login(code);
    }

}
