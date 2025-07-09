package com.example.DongNaeJoGak.domain.idea.member.dto.response;

import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDTO {

    @Getter
    @Builder
    public static class MemberInfoResponse {
        private String email;
        private String username;
        private String image;

        public static MemberResponseDTO.MemberInfoResponse toMemberInfoResponse(Member member) {
            return MemberInfoResponse.builder()
                    .email(member.getEmail())
                    .username(member.getUsername())
                    .image(member.getProfileImage())
                    .build();
        }
    }
}
