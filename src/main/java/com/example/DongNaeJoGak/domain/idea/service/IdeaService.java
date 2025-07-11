package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IdeaService {

    IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request, MultipartFile image, Member member) throws IOException;

    IdeaResponseDTO.DetailIdeaResponse getDetailIdea(Long ideaId);

    IdeaResponseDTO.ListIdeaResponse getIdeasInMap(Double leftLat, Double leftLong, Double rightLat, Double rightLong);

    IdeaResponseDTO.ListIdeaResponse getNearbyIdeas(Long cursor, Integer size, Long ideaId);

}
