package com.example.DongNaeJoGak.domain.idea.repository;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.IdeaReaction;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaReactionRepository extends JpaRepository<IdeaReaction, Long> {

    IdeaReaction findByIdeaAndMember(Idea idea, Member member);

    void delete(IdeaReaction ideaReaction);

    // 마이페이지: 내가 좋아요한 아이디어 최신순
    List<IdeaReaction> findByMemberIdAndReactionTypeOrderByCreatedAtDesc(Long memberId, IdeaReactionType reactionType);
}
