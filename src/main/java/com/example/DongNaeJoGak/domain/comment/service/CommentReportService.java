package com.example.DongNaeJoGak.domain.comment.service;

import com.example.DongNaeJoGak.domain.comment.dto.request.CommentReportRequestDTO;
import com.example.DongNaeJoGak.domain.comment.dto.response.CommentReportResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;

public interface CommentReportService {

    CommentReportResponseDTO reportComment(Long commentId, CommentReportRequestDTO request, Member member);
}
