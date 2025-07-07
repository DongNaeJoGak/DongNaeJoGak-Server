package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.OAuth2ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.MemberException;
import com.example.DongNaeJoGak.global.apiPayload.exception.OAuth2Exception;
import com.example.DongNaeJoGak.global.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Map<String, OAuth2Service> oAuth2ServiceMap;
    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public OAuthResponseDTO.LoginResponse login(ProviderType providerType, String code, String state) {
        OAuth2Service service = oAuth2ServiceMap.get(providerType.name());

        if (service == null) {
            throw new OAuth2Exception(OAuth2ErrorStatus.UNSUPPORTED_OAUTH2_PROVIDER);
        }

        System.out.println("code:" + code);
        System.out.println("state:" + state);

        return service.login(providerType, code, state);
    }

    @Override
    public OAuthResponseDTO.RefreshTokenResponse reissueToken(OAuthRequestDTO.RefreshTokenRequest request) {

        Long memberId = jwtTokenUtil.getUserIdFromToken(request.getRefreshToken());

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));

        return OAuthResponseDTO.RefreshTokenResponse.torefreshTokenResponse(jwtTokenUtil.createAccessToken(member));
    }


}
