package com.example.DongNaeJoGak.global.apiPayload.code.status.error;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IdeaErrorStatus implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "IDEA404", "아이디어를 찾지 못했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
