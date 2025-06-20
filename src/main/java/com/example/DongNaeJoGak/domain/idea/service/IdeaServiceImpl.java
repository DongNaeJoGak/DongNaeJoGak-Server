package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.IdeaErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.IdeaException;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;

    @Override
    public IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request) {
        Idea idea = Idea.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        return IdeaResponseDTO.CreateIdeaResponse.toCreateIdeaResponse(ideaRepository.save(idea));
    }

    @Override
    public IdeaResponseDTO.DetailIdeaResponse getDetailIdea(Long ideaId) {
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        return IdeaResponseDTO.DetailIdeaResponse.toDetailIdeaResponse(idea);
    }

    @Override
    public IdeaResponseDTO.ListIdeaResponse getIdeasInMap(Double leftLat, Double leftLong, Double rightLat, Double rightLong) {
        Slice<Idea> ideas = ideaRepository.findByLatitudeBetweenAndLongitudeBetween(
                Math.min(leftLat, rightLat), Math.max(leftLat, rightLat),
                Math.min(leftLong, rightLong), Math.max(leftLong, rightLong)
        );
        return IdeaResponseDTO.ListIdeaResponse.toListIdeaResponse(ideas);
    }

    @Override
    public IdeaResponseDTO.ListIdeaResponse getNearbyIdeas(Long cursor, Integer size, Long ideaId) {
        Idea currentIdea = ideaRepository.findById(ideaId).orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        Double currentIdeaLat = currentIdea.getLatitude();
        Double currentIdeaLong = currentIdea.getLongitude();

        Pageable pageable = PageRequest.of(0, size);

        Slice<Idea> ideaSlice = ideaRepository.findNearbyIdeas(currentIdeaLat, currentIdeaLong, cursor, pageable);

        return IdeaResponseDTO.ListIdeaResponse.toListIdeaResponse(ideaSlice);

    }
}
