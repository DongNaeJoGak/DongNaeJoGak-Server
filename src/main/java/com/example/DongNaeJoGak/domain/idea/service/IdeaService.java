package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;

public interface IdeaService {

    IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request);
}
