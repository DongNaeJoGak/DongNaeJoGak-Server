package com.example.DongNaeJoGak.domain.idea.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaReactionResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.service.IdeaReactionService;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "아이디어 반응 API", description = "아이디어 좋아요/싫어요 반응 처리 API")
public class IdeaReactionController {

    public final IdeaReactionService ideaReactionService;

    @Operation(
            summary = "아이디어 좋아요/싫어요 등록 or 취소",
            description = """
                    특정 아이디어에 대해 좋아요 또는 싫어요 반응을 등록하거나 취소합니다.
                    이미 동일한 반응이 있으면 반응이 취소됩니다.
                    """
    )
    @PostMapping("/api/ideaReactions/{ideaId}")
    public ApiResponse<IdeaReactionResponseDTO.IdeaReactionResposne> reactToIdea(
            @Parameter(hidden = true) @AuthenticatedMember Member member,

            @Parameter(description = "반응할 아이디어 ID", example = "1")
            @PathVariable Long ideaId,

            @Parameter(description = "반응 타입 (LIKE or DISLIKE)", example = "LIKE")
            @RequestParam IdeaReactionType reactionType) {

        IdeaReactionResponseDTO.IdeaReactionResposne resposne =
                ideaReactionService.reactIdea(ideaId, reactionType, member);
        return ApiResponse.onSuccess(resposne);
    }
}
