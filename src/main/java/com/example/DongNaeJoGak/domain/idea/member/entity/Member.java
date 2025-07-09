package com.example.DongNaeJoGak.domain.idea.member.entity;

import com.example.DongNaeJoGak.domain.comment.entity.Comment;
import com.example.DongNaeJoGak.domain.idea.entity.Idea;
import com.example.DongNaeJoGak.domain.idea.member.entity.enums.ProviderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private ProviderType providerType;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Idea> ideas;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public void updateInfo(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }
}
