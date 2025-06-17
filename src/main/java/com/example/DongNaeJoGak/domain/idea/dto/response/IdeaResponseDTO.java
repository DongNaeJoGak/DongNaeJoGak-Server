package com.example.DongNaeJoGak.domain.idea.dto.response;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

public class IdeaResponseDTO {

    @Getter
    @Builder
    public static class CreateIdeaResponse {
        private Long ideaId;

        public static CreateIdeaResponse toCreateIdeaResponse(Idea idea) {
            return CreateIdeaResponse.builder()
                    .ideaId(idea.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ListIdeaResponse {
        private List<DetailIdeaResponse> ideas;
        private Long cursor;
        private Boolean hasNext;

        public static ListIdeaResponse toListIdeaResponse(Slice<Idea> ideaSlice) {
            List<Idea> ideaList = ideaSlice.getContent();
            return ListIdeaResponse.builder()
                    .ideas(ideaList.stream().map(DetailIdeaResponse::toDetailIdeaResponse).toList())
                    .cursor(ideaSlice.hasNext() ? ideaList.get(ideaList.size() - 1).getId() : null)
                    .hasNext(ideaSlice.hasNext())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class DetailIdeaResponse {
        private Long ideaId;
        private Double latitude;
        private Double longitude;
        private String title;
        private String content;
        private String imageUrl;
        private IdeaStatus status;
        private Long likeNum;
        private Long dislikeNum;

        public static DetailIdeaResponse toDetailIdeaResponse(Idea idea) {
            return DetailIdeaResponse.builder()
                    .ideaId(idea.getId())
                    .latitude(idea.getLatitude())
                    .longitude(idea.getLongitude())
                    .title(idea.getTitle())
                    .content(idea.getContent())
                    .imageUrl(idea.getImageUrl())
                    .likeNum(idea.getLikeNum())
                    .dislikeNum(idea.getDislikeNum())
                    .build();
        }
    }
}
