package com.example.DongNaeJoGak.domain.member.dto.response;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import lombok.Data;

@Data
public class MyPageCommentDTO {
    private Long id;
    private String content;

    public static MyPageCommentDTO from(Comment comment) {
        MyPageCommentDTO dto = new MyPageCommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        return dto;
    }
}
