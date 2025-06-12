package com.example.DongNaeJoGak.domain.idea.repository;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
