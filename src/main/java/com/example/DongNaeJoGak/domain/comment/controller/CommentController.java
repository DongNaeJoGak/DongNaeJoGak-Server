package com.example.DongNaeJoGak.domain.comment.controller;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentReportRequestDTO;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateCommentRequest;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateReplyRequest;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentReportResponseDTO;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.*;
import com.example.DongNaeJoGak.domain.comment.service.CommentReportService;
import com.example.DongNaeJoGak.domain.comment.service.CommentService;
import com.example.DongNaeJoGak.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ideas/{ideaId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentReportService commentReportService;

    // 댓글 생성
    @PostMapping
    public ApiResponse<CreateCommentResponse> createComment(
            @PathVariable Long ideaId,
            @RequestBody CreateCommentRequest request
    ) {
        CreateCommentResponse response = commentService.createComment(ideaId, request);
        return ApiResponse.onSuccess(response);
    }

    // 대댓글 생성
    @PostMapping("/{parentId}/replies")
    public ApiResponse<CreateCommentResponse> createReply(
            @PathVariable Long ideaId,
            @PathVariable Long parentId,
            @RequestBody CreateReplyRequest request
    ) {
        CreateCommentResponse response = commentService.createReply(ideaId, parentId, request);
        return ApiResponse.onSuccess(response);
    }

    // 댓글 목록 조회 (무한 스크롤)
    @GetMapping
    public ApiResponse<ListCommentResponse> getComments(
            @PathVariable Long ideaId,
            Pageable pageable
    ) {
        ListCommentResponse response = commentService.getComments(ideaId, pageable);
        return ApiResponse.onSuccess(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ApiResponse.onSuccess(null);
    }

    // 댓글 신고
    @PostMapping("/{commentId}/report")
    public ApiResponse<CommentReportResponseDTO> reportComment(
            @PathVariable Long commentId,
            @RequestBody CommentReportRequestDTO request
    ) {
        CommentReportResponseDTO response = commentReportService.reportComment(commentId, request);
        return ApiResponse.onSuccess(response);
    }
}
