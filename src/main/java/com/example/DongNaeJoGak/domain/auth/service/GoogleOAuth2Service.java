package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.GoogleOAuth2DTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.NaverOAuth2DTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.domain.idea.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.GoogleOAuth2ConfigData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("GOOGLE")
public class GoogleOAuth2Service extends AbstractOAuth2Service {

    private final GoogleOAuth2ConfigData googleOAuth2ConfigData;

    public GoogleOAuth2Service(MemberRepository memberRepository, TokenService tokenService, GoogleOAuth2ConfigData googleOAuth2ConfigData) {
        super(memberRepository, tokenService);
        this.googleOAuth2ConfigData = googleOAuth2ConfigData;
    }

    @Override
    public String getAccessToken(String code, String state) {
        // 인가코드 토큰 가져오기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", googleOAuth2ConfigData.getClientId());
        map.add("client_secret", googleOAuth2ConfigData.getClientSecret());
        map.add("redirect_uri", googleOAuth2ConfigData.getRedirectUri());
        map.add("code", code);
        map.add("state", state);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response1 = restTemplate.exchange(
                googleOAuth2ConfigData.getTokenUri(),
                HttpMethod.POST,
                request,
                String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        GoogleOAuth2DTO.OAuth2TokenDTO oAuth2TokenDTO = null;

        try {
            oAuth2TokenDTO = objectMapper.readValue(response1.getBody(), GoogleOAuth2DTO.OAuth2TokenDTO.class);
            return oAuth2TokenDTO.getAccessToken();
        } catch (Exception e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_ACCESS_TOKEN);
        }
    }

    @Override
    protected OAuthRequestDTO.LoginRequest getUserInfo(String token) {
        GoogleOAuth2DTO.GoogleInfoResponse googleInfo = getGoogleProfile(token);

        return OAuthRequestDTO.LoginRequest.builder()
                .providerId(String.valueOf(googleInfo.getSub()))
                .username(googleInfo.getName())
                .email(googleInfo.getEmail())
                .image(googleInfo.getPicture())
                .providerType(ProviderType.GOOGLE)
                .build();
    }

    private GoogleOAuth2DTO.GoogleInfoResponse getGoogleProfile(String token) {
        // 토큰으로 정보 가져오기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<MultiValueMap> request1 = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response2 = restTemplate.exchange(
                googleOAuth2ConfigData.getUserInfoUri(),
                HttpMethod.GET,
                request1,
                String.class
        );

        ObjectMapper om = new ObjectMapper();

        try {
            return om.readValue(response2.getBody(), GoogleOAuth2DTO.GoogleInfoResponse.class);
        } catch(Exception e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_USER_INFO);
        }
    }
}
