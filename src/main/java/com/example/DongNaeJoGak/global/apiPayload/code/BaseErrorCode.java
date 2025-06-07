package com.example.DongNaeJoGak.global.apiPayload.code;

import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();

    default ApiResponse<Void> getErrorResponse() {
        return ApiResponse.onFailure(getCode(), getMessage());
    }
}
