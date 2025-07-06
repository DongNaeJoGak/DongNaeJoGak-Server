package com.example.DongNaeJoGak.domain.member.dto.response;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import lombok.Data;

@Data
public class MyPageIdeaDTO {
    private Long id;
    private String title;

    public static MyPageIdeaDTO from(Idea idea) {
        MyPageIdeaDTO dto = new MyPageIdeaDTO();
        dto.setId(idea.getId());
        dto.setTitle(idea.getTitle());
        return dto;
    }
}
