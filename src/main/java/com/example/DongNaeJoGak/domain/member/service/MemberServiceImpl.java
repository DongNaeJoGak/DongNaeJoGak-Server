package com.example.DongNaeJoGak.domain.member.service;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.comment.repository.CommentRepository;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.entity.IdeaReaction;
import com.example.DongNaeJoGak.domain.idea.entity.enums.IdeaReactionType;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaReactionRepository;
import com.example.DongNaeJoGak.domain.idea.repository.IdeaRepository;
import com.example.DongNaeJoGak.domain.member.dto.request.UpdateProfileRequest;
import com.example.DongNaeJoGak.domain.member.dto.response.MyPageCommentDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.MyPageIdeaDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.MyPageResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.MemberResponseDTO;
import com.example.DongNaeJoGak.domain.member.dto.response.UpdateProfileResponse;
import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.MemberErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final IdeaRepository ideaRepository;
    private final IdeaReactionRepository ideaReactionRepository;
    private final CommentRepository commentRepository;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));
    }

    @Override
    public MemberResponseDTO.MemberInfoResponse findmyInfo(Member member) {
        Member memberInfo = memberRepository.findById(member.getId())
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));

        return MemberResponseDTO.MemberInfoResponse.toMemberInfoResponse(memberInfo);
    }

    @Override
    public MyPageResponseDTO getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND));

        List<MyPageIdeaDTO> myIdeas = ideaRepository.findByMemberId(memberId)
                .stream()
                .map(MyPageIdeaDTO::from)
                .collect(Collectors.toList());

        List<MyPageIdeaDTO> likedIdeas = ideaReactionRepository
                .findByMemberIdAndReactionTypeOrderByCreatedAtDesc(memberId, IdeaReactionType.LIKE)
                .stream()
                .map(IdeaReaction::getIdea)
                .map(MyPageIdeaDTO::from)
                .collect(Collectors.toList());

        List<MyPageCommentDTO> myComments = commentRepository.findByMemberId(memberId)
                .stream()
                .map(MyPageCommentDTO::from)
                .collect(Collectors.toList());

        return MyPageResponseDTO.builder()
                .nickname(member.getUsername())
                .bio(member.getBio())
                .myIdeas(myIdeas)
                .likedIdeas(likedIdeas)
                .myComments(myComments)
                .build();
    }

    // 프로필 수정 추가
    @Override
    public UpdateProfileResponse updateProfile(Member member, UpdateProfileRequest request) {
        member.updateInfo(request.getUsername(), member.getProfileImage());
        member.updateBio(request.getBio());
        memberRepository.save(member); // 영속성 반영

        return UpdateProfileResponse.builder()
                .username(member.getUsername())
                .bio(member.getBio())
                .build();
    }
}
