package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaReactionResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.IdeaReaction;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaReactionRepository;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.IdeaErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.IdeaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IdeaReactionServiceImpl implements IdeaReactionService {

    public final IdeaRepository ideaRepository;
    public final IdeaReactionRepository ideaReactionRepository;

    @Override
    @Transactional
    public IdeaReactionResponseDTO.IdeaReactionResposne reactIdea(Long ideaId, IdeaReactionType ideaReactionType, Member member) {
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        IdeaReaction existingReaction = ideaReactionRepository.findByIdeaAndMember(idea, member);

        if (existingReaction != null) {

            IdeaReactionType existingType = existingReaction.getReactionType();

            if (existingType == ideaReactionType) {
                // 동일한 반응을 또 누르면 반응 취소
                ideaReactionRepository.delete(existingReaction);
                if (existingReaction.getReactionType() == IdeaReactionType.LIKE) {
                    idea.setLikeNum(idea.getLikeNum() -1);
                } else {
                    idea.setDislikeNum(idea.getDislikeNum() -1);
                }
                return IdeaReactionResponseDTO.IdeaReactionResposne.toIdeaReactionResposne(idea, null);

            } else {
                // 다른 반응을 누르면 기존 반응 삭제 , 새로운 반응 저장
                ideaReactionRepository.delete(existingReaction);
                if (existingReaction.getReactionType() == IdeaReactionType.LIKE) {
                    idea.setLikeNum(idea.getLikeNum() -1);
                } else {
                    idea.setDislikeNum(idea.getDislikeNum() -1);
                }
            }
        }

        // 새로운 반응 저장
        IdeaReaction newReaction = IdeaReaction.builder()
                .idea(idea)
                .member(member)
                .reactionType(ideaReactionType)
                .build();

        ideaReactionRepository.save(newReaction);

        if (ideaReactionType == IdeaReactionType.LIKE) {
            idea.setLikeNum(idea.getLikeNum() + 1);
        } else {
            idea.setDislikeNum(idea.getDislikeNum() + 1);
        }

        return IdeaReactionResponseDTO.IdeaReactionResposne.toIdeaReactionResposne(idea, ideaReactionType);
    }
}
