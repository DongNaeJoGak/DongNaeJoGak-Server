package com.example.DongNaeJoGak.domain.member.dto.response;

import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.member.entity.Member;
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
