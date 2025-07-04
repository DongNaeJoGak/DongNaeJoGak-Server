package com.example.DongNaeJoGak.domain.idea.repository;

import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IdeaRepository extends JpaRepository<Idea, Long> {

    Slice<Idea> findByLatitudeBetweenAndLongitudeBetween(Double latMin, Double latMax, Double lonMin, Double lonMax);

    @Query("""
        SELECT i FROM Idea i
        WHERE i.latitude BETWEEN :latMin AND :latMax
          AND i.longitude BETWEEN :lonMin AND :lonMax
          AND i.id < :cursor
        ORDER BY i.id DESC
    """)
    Slice<Idea> findNearbyIdeas(
            @Param("latMin") Double latMin,
            @Param("latMax") Double latMax,
            @Param("lonMin") Double lonMin,
            @Param("lonMax") Double lonMax,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
