package com.example.DongNaeJoGak.domain.comment.dto.request;

import lombok.Getter;

public class CommentRequestDTO {

    @Getter
    public static class CreateCommentRequest {
        private String content;    // 댓글 내용
        // 아이디어 ID는 URL로 받음 (@PathVariable)
    }

    @Getter
    public static class CreateReplyRequest {
        private String content;       // 대댓글 내용
        // 아이디어 ID + 부모 댓글 ID는 URL로 받음
    }
}
