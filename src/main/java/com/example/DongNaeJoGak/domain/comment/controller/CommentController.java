package com.example.DongNaeJoGak.domain.comment.controller;

import com.example.DongNaeJoGak.domain.auth.annotation.AuthenticatedMember;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentReportRequestDTO;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateCommentRequest;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateReplyRequest;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentReportResponseDTO;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.*;
import com.example.DongNaeJoGak.domain.comment.service.CommentReportService;
import com.example.DongNaeJoGak.domain.comment.service.CommentService;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ideas/{ideaId}/comments")
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "아이디어 댓글 및 대댓글, 신고 관련 API")
@SecurityRequirement(name = "BearerAuth") // 🔑 전체 컨트롤러에 JWT 적용!
public class CommentController {

    private final CommentService commentService;
    private final CommentReportService commentReportService;

    @Operation(summary = "댓글 생성", description = "아이디어에 새로운 댓글을 작성합니다.")
    @PostMapping
    public ApiResponse<CreateCommentResponse> createComment(
            @AuthenticatedMember Member member,
            @Parameter(description = "아이디어 ID", example = "1")
            @PathVariable Long ideaId,
            @RequestBody CreateCommentRequest request
    ) {
        CreateCommentResponse response = commentService.createComment(ideaId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "대댓글 생성", description = "특정 댓글에 대한 대댓글을 작성합니다.")
    @PostMapping("/{parentId}/replies")
    public ApiResponse<CreateCommentResponse> createReply(
            @AuthenticatedMember Member member,
            @Parameter(description = "아이디어 ID", example = "1")
            @PathVariable Long ideaId,
            @Parameter(description = "부모 댓글 ID", example = "10")
            @PathVariable Long parentId,
            @RequestBody CreateReplyRequest request
    ) {
        CreateCommentResponse response = commentService.createReply(ideaId, parentId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "댓글 목록 조회", description = "해당 아이디어의 댓글 목록을 페이지네이션으로 조회합니다.")
    @GetMapping
    public ApiResponse<ListCommentResponse> getComments(
            @Parameter(description = "아이디어 ID", example = "1")
            @PathVariable Long ideaId,
            @Parameter(description = "페이지 번호(0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ListCommentResponse response = commentService.getComments(ideaId, pageable);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @AuthenticatedMember Member member,
            @Parameter(description = "삭제할 댓글 ID", example = "5")
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "댓글 신고", description = "특정 댓글을 신고합니다.")
    @PostMapping("/{commentId}/report")
    public ApiResponse<CommentReportResponseDTO> reportComment(
            @AuthenticatedMember Member member,
            @Parameter(description = "신고할 댓글 ID", example = "5")
            @PathVariable Long commentId,
            @RequestBody CommentReportRequestDTO request
    ) {
        CommentReportResponseDTO response = commentReportService.reportComment(commentId, request);
        return ApiResponse.onSuccess(response);
    }
}
