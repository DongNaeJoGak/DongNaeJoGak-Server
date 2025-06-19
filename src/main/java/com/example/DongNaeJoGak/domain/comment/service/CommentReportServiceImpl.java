package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentReportRequestDTO;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentReportResponseDTO;
import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.entity.CommentReport;
import com.example.DongNaeJoGak.domain.comment.repository.CommentReportRepository;
import com.example.DongNaeJoGak.domain.comment.repository.CommentRepository;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.ErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReportServiceImpl implements CommentReportService {

    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    private final MemberRepository memberRepository;

    // 현재 로그인한 유저 (임시 로직 -> 추후 인증으로 교체)
    private Member getCurrentMember() {
        return memberRepository.findById(1L) // 테스트용으로 1L 고정
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public CommentReportResponseDTO reportComment(Long commentId, CommentReportRequestDTO request) {
        Member reporter = getCurrentMember(); // 나중에 Security에서 받아오도록 수정 필요

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        // 자신이 작성한 댓글을 신고할 수 없음
        if (comment.getMember().getId().equals(reporter.getId())) {
            throw new GeneralException(ErrorStatus.CANNOT_REPORT_OWN_COMMENT);
        }

        // 이미 신고했는지 확인
        if (commentReportRepository.existsByMemberAndComment(reporter, comment)) {
            throw new GeneralException(ErrorStatus.ALREADY_REPORTED);
        }

        // 신고 저장
        CommentReport commentReport = CommentReport.builder()
                .member(reporter)
                .comment(comment)
                .reportType(request.getReason())
                .build();
        commentReportRepository.save(commentReport);

        // 신고 수 증가
        comment.addReport(); // Comment 엔티티에 메서드 정의 필요
        commentRepository.save(comment);

        return CommentReportResponseDTO.of(comment.getId(), "댓글이 성공적으로 신고되었습니다.");
    }
}