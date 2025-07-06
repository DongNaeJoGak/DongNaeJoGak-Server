package com.example.DongNaeJoGak.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProfileResponse {
    private String username;
    private String bio;
}
