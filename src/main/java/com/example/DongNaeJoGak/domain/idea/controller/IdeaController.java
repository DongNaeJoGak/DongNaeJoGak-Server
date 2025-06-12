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

//    @GetMapping("/api/ideas/{ideaId}")
//    public ApiResponse<IdeaResponseDTO.DetailIdeaResponse> getIdea(@PathVariable Long ideaId) {
//
//    }
//
//    @GetMapping("/api/ideas")
//    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getIdeas(@RequestParam Double latitude, @RequestParam Double longitude) {
//
//    }
//
//
//    @GetMapping("/api/ideas/{ideaId}/nearby")
//    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getNearbyIdeas(@RequestParam Long cursor, @RequestParam Long size,
//                                                                        @PathVariable Long ideaId) {
//
//    }
}
