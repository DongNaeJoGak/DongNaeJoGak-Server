package com.example.DongNaeJoGak.domain.member.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.member.dto.request.UpdateProfileRequest;
import com.example.DongNaeJoGak.domain.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.MyPageResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.UpdateProfileResponse;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.service.MemberService;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 정보, 마이페이지, 프로필 관리 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "내 회원 정보 조회",
            description = "JWT 인증 정보를 사용하여 로그인한 사용자의 기본 정보를 조회합니다."
    )
    @GetMapping("/api/members/info")
    public ApiResponse<MemberResponseDTO.MemberInfoResponse> getMyInfo(
            @Parameter(hidden = true) @AuthenticatedMember Member member) {

        MemberResponseDTO.MemberInfoResponse response = memberService.findmyInfo(member);
        return ApiResponse.onSuccess(response);
    }

    @Operation(
            summary = "내 마이페이지 조회",
            description = """
                    로그인한 사용자의 마이페이지 정보를 조회합니다.
                    반환 데이터는 내 닉네임(username), 자기소개(bio),
                    내가 작성한 아이디어 리스트, 좋아요한 아이디어 리스트,
                    내가 작성한 댓글 리스트를 포함합니다.
                    """
    )
    @GetMapping("/api/members/mypage")
    public ApiResponse<MyPageResponseDTO> getMyPage(
            @Parameter(hidden = true) @AuthenticatedMember Member member) {

        MyPageResponseDTO response = memberService.getMyPage(member.getId());
        return ApiResponse.onSuccess(response);
    }

    @Operation(
            summary = "내 프로필 수정",
            description = """
                    로그인한 사용자의 닉네임(username)과 bio(소개글)을 수정합니다.
                    요청 바디에 username과 bio를 JSON으로 전달해야 합니다.
                    """
    )
    @PatchMapping("/api/members/profile")
    public ApiResponse<UpdateProfileResponse> updateProfile(
            @Parameter(hidden = true) @AuthenticatedMember Member member,
            @RequestBody UpdateProfileRequest request) {

        UpdateProfileResponse response = memberService.updateProfile(member, request);
        return ApiResponse.onSuccess(response);
    }

}
