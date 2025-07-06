package com.example.DongNaeJoGak.domain.member.dto.request;

import lombok.Getter;

@Getter
public class UpdateProfileRequest {
    private String username; // 닉네임
    private String bio;      // 소개글
}
