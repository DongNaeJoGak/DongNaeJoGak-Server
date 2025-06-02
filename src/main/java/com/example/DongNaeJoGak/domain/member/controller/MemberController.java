package com.example.DongNaeJoGak.domain.member.controller;

import com.example.dongnaejogak.domain.member.entitiy.Member;
import com.example.dongnaejogak.domain.member.repository.MemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public Member saveUser(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @GetMapping
    public List<Member> getUsers() {
        return memberRepository.findAll();
    }

    @DeleteMapping("/{member_id}")
    public void deleteUser(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }

}
