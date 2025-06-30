package com.example.DongNaeJoGak.global.apiPayload.code.status.error;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorStatus implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "댓글을 찾지 못했습니다."),
    INVALID_PARENT(HttpStatus.BAD_REQUEST, "COMMENT4001", "답글을 달 수 없는 댓글입니다."), // 유효하지 않은 부모 댓글
    SELF_REPORT(HttpStatus.BAD_REQUEST, "COMMENT4002", "자신의 댓글은 신고할 수 없습니다."),
    ALREADY_REPORTED(HttpStatus.CONFLICT, "COMMENT409", "이미 신고한 댓글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
