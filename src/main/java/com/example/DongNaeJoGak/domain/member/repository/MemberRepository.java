package com.example.DongNaeJoGak.domain.member.repository;

import com.example.dongnaejogak.domain.member.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}