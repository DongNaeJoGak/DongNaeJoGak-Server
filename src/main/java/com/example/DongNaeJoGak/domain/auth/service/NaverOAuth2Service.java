package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.NaverOAuth2DTO;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.data.NaverOAuth2ConfigData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("naver")
public class NaverOAuth2Service extends AbstractOAuth2Service {

    private final NaverOAuth2ConfigData naverOAuth2ConfigData;

    public NaverOAuth2Service(MemberRepository memberRepository, TokenService tokenService, NaverOAuth2ConfigData naverOAuth2ConfigData) {
        super(memberRepository, tokenService);
        this.naverOAuth2ConfigData = naverOAuth2ConfigData;
    }

    @Override
    public String getAccessToken(String code) {
        // 인가코드 토큰 가져오기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", naverOAuth2ConfigData.getClientId());
        map.add("client_secret", naverOAuth2ConfigData.getClientSecret());
        map.add("redirect_uri", naverOAuth2ConfigData.getRedirectUri());
        map.add("code", code);
        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response1 = restTemplate.exchange(
                naverOAuth2ConfigData.getTokenUri(),
                HttpMethod.POST,
                request,
                String.class);


        System.out.println("Naver 응답 본문: " + response1.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        NaverOAuth2DTO.OAuth2TokenDTO oAuth2TokenDTO = null;

        try {
            oAuth2TokenDTO = objectMapper.readValue(response1.getBody(), NaverOAuth2DTO.OAuth2TokenDTO.class);
            return oAuth2TokenDTO.getAccess_token();
        } catch (Exception e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_ACCESS_TOKEN);
        }
    }

    @Override
    protected OAuthRequestDTO.LoginRequest getUserInfo(String token) {
        NaverOAuth2DTO.NaverProfile naverProfile = getNaverProfile(token);
        return OAuthRequestDTO.LoginRequest.builder()
                .providerId(String.valueOf(naverProfile.getResponse().getId()))
                .username(naverProfile.getResponse().getNickname())
                .image(naverProfile.getResponse().getProfile_image())
                .providerType(ProviderType.NAVER)
                .build();
    }

    private NaverOAuth2DTO.NaverProfile getNaverProfile(String token) {
        // 토큰으로 정보 가져오기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<MultiValueMap> request1 = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response2 = restTemplate.exchange(
                naverOAuth2ConfigData.getUserInfoUri(),
                HttpMethod.GET,
                request1,
                String.class
        );

        ObjectMapper om = new ObjectMapper();

        try {
            return om.readValue(response2.getBody(), NaverOAuth2DTO.NaverProfile.class);
        } catch(Exception e) {
            throw new OAuth2Exception(OAuth2ErrorStatus.FAIL_USER_INFO);
        }
    }


}
