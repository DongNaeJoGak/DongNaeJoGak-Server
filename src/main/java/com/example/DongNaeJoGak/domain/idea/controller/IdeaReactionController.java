package com.example.DongNaeJoGak.domain.idea.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaReactionResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.service.IdeaReactionService;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IdeaReactionController {

    public final IdeaReactionService ideaReactionService;

    @Operation(
            summary = "아이디어 좋아요/싫어요 반응",
            description = "특정 아이디어에 대해 좋아요 또는 싫어요 반응을 등록하거나 취소합니다."
    )
    @PostMapping("/api/ideaReactions/{ideaId}")
    public ApiResponse<IdeaReactionResponseDTO.IdeaReactionResposne> reactToIdea(@AuthenticatedMember Member member,
                                                            @PathVariable Long ideaId,
                                                            @RequestParam IdeaReactionType reactionType) {

        IdeaReactionResponseDTO.IdeaReactionResposne resposne = ideaReactionService.reactIdea(ideaId, reactionType, member);
        return ApiResponse.onSuccess(resposne);

    }
}
