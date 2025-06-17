package com.example.DongNaeJoGak.domain.idea.entity;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaStatus;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "idea")
public class Idea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    private String imageUrl;

    @Builder.Default
    private Long likeNum = 0L;

    @Builder.Default
    private Long dislikeNum = 0L;

    @Column(name = "idea_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IdeaStatus status = IdeaStatus.VOTING;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL)
    private List<Comment> comments;
}

