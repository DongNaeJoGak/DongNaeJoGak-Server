package com.example.DongNaeJoGak.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MyPageResponseDTO {
    private String nickname;
    private String bio;
    private List<MyPageIdeaDTO> myIdeas;
    private List<MyPageIdeaDTO> likedIdeas;
    private List<MyPageCommentDTO> myComments;
}
