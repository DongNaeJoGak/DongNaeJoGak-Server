package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.entity.enums.ProviderType;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractOAuth2Service implements OAuth2Service {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public OAuthResponseDTO.LoginResponse login(ProviderType providerType, String code, String state) {
        String accessToken = getAccessToken(code, state);
        OAuthRequestDTO.LoginRequest request = getUserInfo(accessToken);
        request.setProviderType(providerType);

        Member member = saveOrUpdateMember(request);

        return tokenService.createLoginToken(member);
    }

    protected abstract String getAccessToken(String code, String state);

    protected abstract OAuthRequestDTO.LoginRequest getUserInfo(String token);

    private Member saveOrUpdateMember(OAuthRequestDTO.LoginRequest request) {
        System.out.println("==== [회원 정보 저장 직전 로그] ====");
        System.out.println("username: " + request.getUsername());
        System.out.println("email: " + request.getEmail());
        System.out.println("profileImage: " + request.getImage());
        System.out.println("providerId: " + request.getProviderId());
        System.out.println("providerType: " + request.getProviderType());
        System.out.println("====================================");

        Member member;
        Optional<Member> memberOptional = memberRepository.findByProviderIdAndProviderType(request.getProviderId(), request.getProviderType());

        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            member.updateInfo(request.getUsername(), request.getImage());

        } else {
            member = OAuthResponseDTO.LoginResponse.toMember(request);
        }

        return memberRepository.save(member);
    }
}
