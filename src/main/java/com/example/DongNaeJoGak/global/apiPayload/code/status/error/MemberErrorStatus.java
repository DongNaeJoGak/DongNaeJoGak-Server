package com.example.DongNaeJoGak.global.apiPayload.code.status.error;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorStatus implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "사용자를 찾지 못했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
