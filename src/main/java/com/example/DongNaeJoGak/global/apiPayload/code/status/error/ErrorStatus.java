package com.example.DongNaeJoGak.global.apiPayload.code.status.error;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 일반적인 ERROR 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _BAD_REQUEST_SAME_STATE(HttpStatus.BAD_REQUEST, "COMMON4002", "수정하려는 데이터가 현재 상태와 동일합니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 유효성 검사
    _VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALID400", "잘못된 파라미터 입니다."),

    // 댓글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "댓글을 찾지 못했습니다."),
    COMMENT_INVALID_PARENT(HttpStatus.BAD_REQUEST, "COMMENT4001", "답글을 달 수 없는 댓글입니다."), // 유효하지 않은 부모 댓글
    COMMENT_SELF_REPORT(HttpStatus.BAD_REQUEST, "COMMENT4002", "자신의 댓글은 신고할 수 없습니다."),
    COMMENT_ALREADY_REPORTED(HttpStatus.CONFLICT, "COMMENT409", "이미 신고한 댓글입니다."),

    // 아이디어
    IDEA_NOT_FOUND(HttpStatus.NOT_FOUND, "IDEA404", "해당 아이디어를 찾을 수 없습니다."),

    // 멤버
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "해당 유저를 찾을 수 없습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
