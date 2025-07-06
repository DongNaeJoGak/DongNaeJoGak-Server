package com.example.DongNaeJoGak.domain.idea.dto.response;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import lombok.Builder;
import lombok.Getter;

public class IdeaReactionResponseDTO {

    @Getter
    @Builder
    public static class IdeaReactionResposne {
        private IdeaReactionType memberReactionType;
        private Long likeNum;
        private Long dislikeNum;

        public static IdeaReactionResposne toIdeaReactionResposne(Idea idea, IdeaReactionType memberReactionType) {
            return IdeaReactionResposne.builder()
                    .memberReactionType(memberReactionType)
                    .likeNum(idea.getLikeNum())
                    .dislikeNum(idea.getDislikeNum())
                    .build();
        }
    }
}
