package com.example.DongNaeJoGak.domain.comment.entity;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private Long commentReportNum = 0L;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id")
    private Idea idea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> commentList = new ArrayList<>();

    // 댓글 숨김 처리한 후에도 대댓글은 계속 보여줄 수 있음
    public void softDelete(LocalDateTime deletedTime) {
        this.deletedAt = deletedTime;
    }

    // 신고 수 파악
    public void addReport() {
        this.commentReportNum += 1;
    }
}
