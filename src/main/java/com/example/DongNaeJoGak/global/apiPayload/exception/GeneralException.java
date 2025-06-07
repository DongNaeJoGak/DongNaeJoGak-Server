package com.example.DongNaeJoGak.global.apiPayload.exception;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public GeneralException(BaseErrorCode errorCode) {
        this.code = errorCode;
    }
}
