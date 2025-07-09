package com.example.DongNaeJoGak.global.apiPayload.code.status.error;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OAuth2ErrorStatus implements BaseErrorCode {
    UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.BAD_REQUEST, "OAUTH400", "지원하지 않는 소셜 로그인입니다."),
    INVALID_STATE(HttpStatus.BAD_REQUEST, "OAUTH400", "state가 존재하지 않습니다"),
    FAIL_OAUTH2_LOGIN(HttpStatus.UNAUTHORIZED, "OAUTH401", "소셜로그인에 실패했습니다."),
    FAIL_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH401", "소셜로그인 엑세스 토큰을 가져오지 못했습니다."),
    FAIL_USER_INFO(HttpStatus.UNAUTHORIZED, "OAUTH401", "소셜로그인 사용자 정보 가져오지 못했습니다."),
    FAIL_REDIRECTION(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH500", "프론트 리디렉션에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
