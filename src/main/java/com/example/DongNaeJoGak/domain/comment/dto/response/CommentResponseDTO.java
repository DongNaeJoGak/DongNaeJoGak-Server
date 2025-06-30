package com.example.DongNaeJoGak.domain.comment.dto.response;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentResponseDTO {

    @Getter
    @Builder
    public static class CreateCommentResponse {
        private Long commentId;

        public static CreateCommentResponse from(Comment comment) {
            return CreateCommentResponse.builder()
                    .commentId(comment.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ReplyResponse {
        private Long replyId;
        private String content;
        private String writerName;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;

        public static ReplyResponse from(Comment reply) {
            return ReplyResponse.builder()
                    .replyId(reply.getId())
                    .content(getVisibleContent(reply))
                    .writerName(reply.getMember().getUsername())
                    .createdAt(reply.getCreatedAt())
                    .deletedAt(reply.getDeletedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class DetailCommentResponse {
        private Long commentId;
        private String content;
        private String writerName;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;
        private List<ReplyResponse> replies;

        public static DetailCommentResponse from(Comment comment) {
            return DetailCommentResponse.builder()
                    .commentId(comment.getId())
                    .content(getVisibleContent(comment))
                    .writerName(comment.getMember().getUsername())
                    .createdAt(comment.getCreatedAt())
                    .deletedAt(comment.getDeletedAt())
                    .replies(
                            comment.getCommentList().stream()
                                    .map(ReplyResponse::from)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ListCommentResponse {
        private List<DetailCommentResponse> comments;
        private Long cursor;
        private Boolean hasNext;

        public static ListCommentResponse from(Slice<Comment> commentSlice) {
            List<Comment> content = commentSlice.getContent();
            return ListCommentResponse.builder()
                    .comments(content.stream()
                            .map(DetailCommentResponse::from)
                            .collect(Collectors.toList()))
                    .cursor(commentSlice.hasNext() ? content.get(content.size() - 1).getId() : null)
                    .hasNext(commentSlice.hasNext())
                    .build();
        }
    }

    private static String getVisibleContent(Comment comment) {
        return comment.getDeletedAt() != null ? "삭제된 댓글입니다" : comment.getContent();
    }
}
