package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;


public interface IdeaService {

    IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request, Member member);

    IdeaResponseDTO.DetailIdeaResponse getDetailIdea(Long ideaId);

    IdeaResponseDTO.ListIdeaResponse getIdeasInMap(Double leftLat, Double leftLong, Double rightLat, Double rightLong);

    IdeaResponseDTO.ListIdeaResponse getNearbyIdeas(Long cursor, Integer size, Long ideaId);

}
