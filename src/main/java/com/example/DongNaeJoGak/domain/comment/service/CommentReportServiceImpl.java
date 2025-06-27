package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentReportRequestDTO;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentReportResponseDTO;
import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.entity.CommentReport;
import com.example.DongNaeJoGak.domain.comment.repository.CommentReportRepository;
import com.example.DongNaeJoGak.domain.comment.repository.CommentRepository;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.CommentErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReportServiceImpl implements CommentReportService {

    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    private final MemberRepository memberRepository;

    // 임시 로그인 멤버 (1L 고정)
    private Member getCurrentMember() {
        return memberRepository.findById(1L) // 테스트용으로 1L 고정
                .orElseThrow(() -> new GeneralException(MemberErrorStatus.NOT_FOUND));
    }

    @Override
    public CommentReportResponseDTO reportComment(Long commentId, CommentReportRequestDTO request) {
        Member reporter = getCurrentMember(); // 나중에 Security에서 받아오도록 수정 필요

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(CommentErrorStatus.NOT_FOUND));

        // 자신이 작성한 댓글을 신고할 수 없음
        if (comment.getMember().getId().equals(reporter.getId())) {
            throw new GeneralException(CommentErrorStatus.SELF_REPORT);
        }

        // 이미 신고했는지 확인
        if (commentReportRepository.existsByMemberAndComment(reporter, comment)) {
            throw new GeneralException(CommentErrorStatus.ALREADY_REPORTED);
        }

        // 신고 저장
        CommentReport commentReport = CommentReport.builder()
                .member(reporter)
                .comment(comment)
                .reportType(request.getReason())
                .build();

        commentReportRepository.save(commentReport);

        comment.addReport(); // 신고 수 +1
        commentRepository.save(comment);

        return CommentReportResponseDTO.of(comment.getId(), "댓글이 성공적으로 신고되었습니다.");
    }
}