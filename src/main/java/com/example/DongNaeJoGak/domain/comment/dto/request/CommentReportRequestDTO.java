package com.example.DongNaeJoGak.domain.comment.dto.request;

import com.example.DongNaeJoGak.domain.comment.entity.enums.CommentReportType;
import lombok.Getter;

@Getter
public class CommentReportRequestDTO {

    private CommentReportType reason; // 신고 사유 ENUM
    // 신고할 댓글 ID는 URL로 입력 받음
}
