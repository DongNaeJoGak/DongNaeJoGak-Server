package com.example.DongNaeJoGak.domain.idea.service;

import com.example.DongNaeJoGak.domain.idea.dto.request.IdeaRequestDTO;
import com.example.DongNaeJoGak.domain.idea.dto.response.IdeaResponseDTO;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.member.entity.Member;
import com.example.DongNaeJoGak.domain.idea.member.repository.MemberRepository;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaReactionRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    public IdeaResponseDTO.CreateIdeaResponse createIdea(IdeaRequestDTO.CreateIdeaRequest request, MultipartFile image, Member member) throws IOException {

        // ✅ S3 업로드
        String imageUrl = s3Service.uploadFile(image);

        // ✅ 엔티티 빌더
        Idea.IdeaBuilder ideaBuilder = Idea.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .imageUrl(imageUrl);

        // ✅ 멤버 연동
        if (member != null) {
            Member memberEntity = memberRepository.findById(member.getId())
                    .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));
            ideaBuilder.member(memberEntity);
        }

        Idea savedIdea = ideaRepository.save(ideaBuilder.build());

        return IdeaResponseDTO.CreateIdeaResponse.toCreateIdeaResponse(savedIdea);
    }

    @Override
    public IdeaResponseDTO.DetailIdeaResponse getDetailIdea(Long ideaId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IdeaException(IdeaErrorStatus.NOT_FOUND));

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
