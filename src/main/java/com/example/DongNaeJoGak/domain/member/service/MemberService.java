package com.example.DongNaeJoGak.domain.member.service;

import com.example.DongNaeJoGak.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
}
