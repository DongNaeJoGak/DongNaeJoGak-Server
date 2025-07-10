package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateCommentRequest;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateReplyRequest;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.CreateCommentResponse;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.ListCommentResponse;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CreateCommentResponse createComment(Long ideaId, CreateCommentRequest request, Member member);

    CreateCommentResponse createReply(Long ideaId, Long parentId, CreateReplyRequest request, Member member);

    ListCommentResponse getComments(Long ideaId, Pageable pageable);

    void deleteComment(Long commentId, Member member);
}
