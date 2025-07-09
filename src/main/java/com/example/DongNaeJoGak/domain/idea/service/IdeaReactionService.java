package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaReactionResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;

public interface IdeaReactionService {

    IdeaReactionResponseDTO.IdeaReactionResposne reactIdea(Long ideaId, IdeaReactionType ideaReactionType, Member member);
}
