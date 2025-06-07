package com.example.DongNaeJoGak.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private final T result;

    //기본적으로 200 OK를 사용하는 성공 응답 생성 메서드
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(), result);
    }

    //상태 코드를 받아서 사용하는 성공 응답 생성 메서드
    public static <T> ApiResponse<T> onSuccess(HttpStatus status, T result) {
        return new ApiResponse<>(true, String.valueOf(status.value()), status.getReasonPhrase(), result);
    }

    //실패 응답 생성 메서드 (데이터 포함)
    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse<>(false, code, message, result);
    }

    //실패 응답 생성 메서드 (데이터 없음)
    public static <T> ApiResponse<T> onFailure(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}
