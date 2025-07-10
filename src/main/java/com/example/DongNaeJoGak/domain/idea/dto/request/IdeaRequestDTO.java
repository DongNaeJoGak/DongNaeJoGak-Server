package com.example.DongNaeJoGak.domain.idea.dto.request;

import lombok.Getter;

public class IdeaRequestDTO {

    @Getter
    public static class CreateIdeaRequest {
        private String title;
        private String content;
        private Double latitude;
        private Double longitude;
    }

    @Getter
    public static class currentLocationRequest {
        private Double currentLatitude;
        private Double currentLongitude;
    }

}
