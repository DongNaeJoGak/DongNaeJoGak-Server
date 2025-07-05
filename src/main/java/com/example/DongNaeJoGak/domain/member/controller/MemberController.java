package com.example.DongNaeJoGak.domain.member.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.service.MemberService;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members/info")
    public ApiResponse<MemberResponseDTO.MemberInfoResponse> getMyInfo(@AuthenticatedMember Member member) {
        MemberResponseDTO.MemberInfoResponse response = memberService.findmyInfo(member);

        return ApiResponse.onSuccess(response);
    }
}
