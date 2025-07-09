package com.example.DongNaeJoGak.domain.idea.member.service;

import com.example.DongNaeJoGak.domain.idea.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;

public interface MemberService {

    Member findById(Long id);

    MemberResponseDTO.MemberInfoResponse findmyInfo(Member member);
}
