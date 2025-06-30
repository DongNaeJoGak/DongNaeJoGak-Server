package com.example.DongNaeJoGak.domain.comment.repository;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.entity.CommentReport;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    // 사용자가 이미 특정 댓글을 신고했는지 여부 확인
    boolean existsByMemberAndComment(Member member, Comment comment);
}
