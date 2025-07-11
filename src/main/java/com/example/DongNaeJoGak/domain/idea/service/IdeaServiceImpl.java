package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.domain.idea.member.repository.MemberRepository;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.IdeaErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.IdeaException;
import com.example.DongNaeJoGak.global.apiPayload.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final MemberRepository memberRepository;

    @Override
    public IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request, Member member) {

        Idea.IdeaBuilder ideaBuilder = Idea.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude());

        if (member != null) {
            // 로그인한 사용자로 아이디어 생성
            Member memberEntity = memberRepository.findById(member.getId()).orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));

            ideaBuilder.member(memberEntity);
        }

        Idea idea = ideaBuilder.build();

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
        Idea currentIdea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

        Double lat = currentIdea.getLatitude();
        Double lon = currentIdea.getLongitude();

        double range = 0.1; // 반경은 필요에 따라 조절

        Double latMin = lat - range;
        Double latMax = lat + range;
        Double lonMin = lon - range;
        Double lonMax = lon + range;

        Pageable pageable = PageRequest.of(0, size);

        Slice<Idea> ideaSlice = ideaRepository.findNearbyIdeas(latMin, latMax, lonMin, lonMax, cursor, pageable);

        return IdeaResponseDTO.ListIdeaResponse.toListIdeaResponse(ideaSlice);
    }


}
