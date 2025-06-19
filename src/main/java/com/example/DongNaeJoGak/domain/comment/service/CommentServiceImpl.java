package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateCommentRequest;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateReplyRequest;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.*;
import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.repository.CommentRepository;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.exception.GeneralException;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IdeaRepository ideaRepository;
    private final MemberRepository memberRepository;

    // 현재 로그인한 유저 (임시 로직 -> 추후 인증으로 교체)
    private Member getCurrentMember() {
        return memberRepository.findById(1L) // 테스트용으로 1L 고정
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public CreateCommentResponse createComment(Long ideaId, CreateCommentRequest request) {
        Member member = getCurrentMember();
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.IDEA_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .idea(idea)
                .member(member)
                .build();

        commentRepository.save(comment);
        return CreateCommentResponse.from(comment);
    }

    @Override
    public CreateCommentResponse createReply(Long ideaId, Long parentId, CreateReplyRequest request) {
        Member member = getCurrentMember();
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.IDEA_NOT_FOUND));
        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        Comment reply = Comment.builder()
                .content(request.getContent())
                .idea(idea)
                .member(member)
                .parent(parent)
                .build();

        commentRepository.save(reply);
        return CreateCommentResponse.from(reply);
    }

    @Override
    public ListCommentResponse getComments(Long ideaId, Pageable pageable) {
        Slice<Comment> comments = commentRepository.findByIdeaIdAndParentIsNull(ideaId, pageable);
        return ListCommentResponse.from(comments);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        comment.softDelete(LocalDateTime.now()); // deletedAt 필드로 소프트 삭제
        commentRepository.save(comment);
    }
}