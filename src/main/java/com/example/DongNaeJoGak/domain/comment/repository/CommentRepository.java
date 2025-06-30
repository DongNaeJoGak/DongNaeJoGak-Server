package com.example.DongNaeJoGak.domain.comment.repository;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 아이디어 ID로 최상위 댓글만 조회 (대댓글 제외) + 스크롤용 Slice
    Slice<Comment> findByIdeaIdAndParentIsNull(Long ideaId, Pageable pageable);

    // 이미 신고했는지 검사용으로 쓸 수도 있음
    boolean existsByMemberIdAndId(Long memberId, Long commentId);
}
