package com.example.DongNaeJoGak.domain.member.service;

import com.example.DongNaeJoGak.domain.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;

public interface MemberService {

    Member findById(Long id);

    MemberResponseDTO.MemberInfoResponse findmyInfo(Member member);
}
