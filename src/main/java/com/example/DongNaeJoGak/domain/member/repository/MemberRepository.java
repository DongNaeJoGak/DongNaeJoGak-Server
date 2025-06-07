package com.example.DongNaeJoGak.domain.member.repository;


import com.example.DongNaeJoGak.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
