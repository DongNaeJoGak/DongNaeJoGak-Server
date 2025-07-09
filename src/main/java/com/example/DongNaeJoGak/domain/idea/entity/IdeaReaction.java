package com.example.DongNaeJoGak.domain.idea.entity;

import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reaction")
public class IdeaReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long id;

    @Column(name = "reaction_type")
    private IdeaReactionType reactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id")
    private Idea idea;
}
