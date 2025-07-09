package com.example.DongNaeJoGak.domain.idea.member.service;

import com.example.DongNaeJoGak.domain.idea.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.domain.idea.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberException(MemberErrorStatus.NOT_FOUND));
    }

    @Override
    public MemberResponseDTO.MemberInfoResponse findmyInfo(Member member) {

        Member memberInfo = memberRepository.findById(member.getId()).orElseThrow(()
                -> new MemberException(MemberErrorStatus.NOT_FOUND));

        return MemberResponseDTO.MemberInfoResponse.toMemberInfoResponse(memberInfo);
    }

}
