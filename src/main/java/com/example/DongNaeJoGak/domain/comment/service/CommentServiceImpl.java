package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateCommentRequest;
import com.example.DongNaeJoGak.domain.comment.dto.request.CommentRequestDTO.CreateReplyRequest;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentResponseDTO.*;
import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.repository.CommentRepository;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.domain.idea.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.exception.CommentException;
import com.example.DongNaeJoGak.global.apiPayload.exception.IdeaException;
import com.example.DongNaeJoGak.global.apiPayload.exception.MemberException;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.CommentErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.IdeaErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
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

    // 현재 로그인한 유저 (임시)
    private Member getCurrentMember() {
        return memberRepository.findById(1L) // 임시 고정
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));
    }

    // 댓글 생성
    @Override
    public CreateCommentResponse createComment(Long ideaId, CreateCommentRequest request) {
        Member member = getCurrentMember();
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .idea(idea)
                .member(member)
                .build();

        commentRepository.save(comment);
        return CreateCommentResponse.from(comment);
    }

    // 대댓글 생성
    @Override
    public CreateCommentResponse createReply(Long ideaId, Long parentId, CreateReplyRequest request) {
        Member member = getCurrentMember();
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new CommentException(CommentErrorStatus.INVALID_PARENT));

        Comment reply = Comment.builder()
                .content(request.getContent())
                .idea(idea)
                .member(member)
                .parent(parent)
                .build();

        commentRepository.save(reply);
        return CreateCommentResponse.from(reply);
    }

    // 댓글 목록 조회
    @Override
    public ListCommentResponse getComments(Long ideaId, Pageable pageable) {
        Slice<Comment> comments = commentRepository.findByIdeaIdAndParentIsNull(ideaId, pageable);
        return ListCommentResponse.from(comments);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorStatus.NOT_FOUND));

        comment.softDelete(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
