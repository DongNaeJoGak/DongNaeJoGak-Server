package com.example.DongNaeJoGak.domain.idea.controller;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.service.IdeaService;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @PostMapping("/api/ideas")
    public ApiResponse<IdeaResponseDTO.CreateIdeaResponse> createIdea(@RequestBody IdeaRequestDTO.CreateIdeaRequest request) {

        IdeaResponseDTO.CreateIdeaResponse createIdeaResponse = ideaService.createIdea(request);

        return ApiResponse.onSuccess(createIdeaResponse);
    }

    @GetMapping("/api/ideas/{ideaId}")
    public ApiResponse<IdeaResponseDTO.DetailIdeaResponse> getIdea(@PathVariable Long ideaId) {

        IdeaResponseDTO.DetailIdeaResponse getDetailIdeaResponse = ideaService.getDetailIdea(ideaId);

        return ApiResponse.onSuccess(getDetailIdeaResponse);
    }

    @GetMapping("/api/ideas")
    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getIdeasInMap(@RequestParam Double leftLat,
                                                                       @RequestParam Double leftLong,
                                                                       @RequestParam Double rightLat,
                                                                       @RequestParam Double rightLong) {

        IdeaResponseDTO.ListIdeaResponse getIdeasInMapResponse = ideaService.getIdeasInMap(leftLat, leftLong, rightLat, rightLong);

        return ApiResponse.onSuccess(getIdeasInMapResponse);
    }


    @GetMapping("/api/ideas/{ideaId}/nearby")
    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getNearbyIdeas(@RequestParam Long cursor, @RequestParam Integer size,
                                                                        @PathVariable Long ideaId) {

        IdeaResponseDTO.ListIdeaResponse getNearbyIdeas = ideaService.getNearbyIdeas(cursor, size, ideaId);

        return ApiResponse.onSuccess(getNearbyIdeas);
    }


}
