package com.example.DongNaeJoGak.domain.member.service;

import com.example.DongNaeJoGak.domain.member.dto.request.UpdateProfileRequest;
import com.example.DongNaeJoGak.domain.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.MyPageResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.UpdateProfileResponse;
import com.example.DongNaeJoGak.domain.member.entity.Member;

public interface MemberService {

    Member findById(Long id);

    MemberResponseDTO.MemberInfoResponse findmyInfo(Member member);

    // 마이페이지 데이터 가져오기
    MyPageResponseDTO getMyPage(Long memberId);

    // 프로필 수정
    UpdateProfileResponse updateProfile(Member member, UpdateProfileRequest request);
}
