package com.example.DongNaeJoGak.domain.auth.service;

import com.example.DongNaeJoGak.domain.auth.dto.request.OAuthRequestDTO;
import com.example.DongNaeJoGak.domain.auth.dto.response.OAuthResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractOAuth2Service implements OAuth2Service {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public OAuthResponseDTO.LoginResponse login(String code) {
        String accessToken = getAccessToken(code);
        OAuthRequestDTO.LoginRequest request = getUserInfo(accessToken);
        Member member = saveOrUpdateMember(request);

        return tokenService.createLoginToken(member);
    }

    protected abstract String getAccessToken(String code);

    protected abstract OAuthRequestDTO.LoginRequest getUserInfo(String token);

    private Member saveOrUpdateMember(OAuthRequestDTO.LoginRequest request) {
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
