package com.example.DongNaeJoGak.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportResponseDTO {
    private Long commentId;
    private String message;

    public static CommentReportResponseDTO of(Long commentId, String message) {
        return CommentReportResponseDTO.builder()
                .commentId(commentId)
                .message(message)
                .build();
    }
}
