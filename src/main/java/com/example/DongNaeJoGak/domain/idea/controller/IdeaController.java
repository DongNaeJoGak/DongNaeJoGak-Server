package com.example.DongNaeJoGak.domain.idea.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.service.IdeaService;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "아이디어 API", description = "아이디어 관련 CRUD API")
public class IdeaController {

    private final IdeaService ideaService;

    @Operation(
            summary = "아이디어 생성",
            description = "새로운 아이디어를 등록합니다."
    )
    @PostMapping("/api/ideas")
    public ApiResponse<IdeaResponseDTO.CreateIdeaResponse> createIdea(
            @RequestBody IdeaRequestDTO.CreateIdeaRequest request) {

        IdeaResponseDTO.CreateIdeaResponse createIdeaResponse = ideaService.createIdea(request);

        return ApiResponse.onSuccess(createIdeaResponse);
    }

    @Operation(
            summary = "아이디어 단건 조회",
            description = "아이디어 ID로 특정 아이디어를 조회합니다."
    )
    @GetMapping("/api/ideas/{ideaId}")
    public ApiResponse<IdeaResponseDTO.DetailIdeaResponse> getIdea(
            @Parameter(description = "조회할 아이디어 ID", example = "1")
            @PathVariable Long ideaId) {

        IdeaResponseDTO.DetailIdeaResponse getDetailIdeaResponse = ideaService.getDetailIdea(ideaId);

        return ApiResponse.onSuccess(getDetailIdeaResponse);
    }

    @Operation(
            summary = "지도 내 아이디어 리스트 조회",
            description = "좌표 범위를 기준으로 지도 내 아이디어들을 조회합니다."
    )
    @GetMapping("/api/ideas")
    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getIdeasInMap(
            @Parameter(description = "좌상단 위도", example = "37.12345")
            @RequestParam Double leftLat,
            @Parameter(description = "좌상단 경도", example = "127.12345")
            @RequestParam Double leftLong,
            @Parameter(description = "우하단 위도", example = "36.98765")
            @RequestParam Double rightLat,
            @Parameter(description = "우하단 경도", example = "128.98765")
            @RequestParam Double rightLong) {

        IdeaResponseDTO.ListIdeaResponse getIdeasInMapResponse =
                ideaService.getIdeasInMap(leftLat, leftLong, rightLat, rightLong);

        return ApiResponse.onSuccess(getIdeasInMapResponse);
    }

    @Operation(
            summary = "근처 아이디어 전체 조회",
            description = "특정 아이디어 기준으로 인근 아이디어들을 페이징 조회합니다."
    )
    @GetMapping("/api/ideas/{ideaId}/nearby")
    public ApiResponse<IdeaResponseDTO.ListIdeaResponse> getNearbyIdeas(
            @Parameter(description = "페이지 커서", example = "0")
            @RequestParam Long cursor,
            @Parameter(description = "페이지 사이즈", example = "10")
            @RequestParam Integer size,
            @Parameter(description = "기준 아이디어 ID", example = "1")
            @PathVariable Long ideaId) {

        IdeaResponseDTO.ListIdeaResponse getNearbyIdeas = ideaService.getNearbyIdeas(cursor, size, ideaId);

        return ApiResponse.onSuccess(getNearbyIdeas);
    }



}

