package com.git.backend.daeng_nyang_connect.review.board.entity;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_idx")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToOne
    @JoinColumn(name = "adopted_animal_idx")
    private AdoptedAnimal adoptedAnimal;

    @Column(name = "text_review")
    private String textReview;

    private Integer like;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images;
}
