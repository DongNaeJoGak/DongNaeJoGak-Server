package com.example.DongNaeJoGak.domain.idea.repository;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.IdeaReaction;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaReactionRepository extends JpaRepository<IdeaReaction, Long> {

    IdeaReaction findByIdeaAndMember(Idea idea, Member member);

    void delete(IdeaReaction ideaReaction);
}
