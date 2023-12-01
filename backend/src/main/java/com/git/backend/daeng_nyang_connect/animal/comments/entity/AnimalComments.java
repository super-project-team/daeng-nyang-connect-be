package com.git.backend.daeng_nyang_connect.animal.comments.entity;

import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animal_comments")
public class AnimalComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_comments_idx")
    private Long commentsId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_board_idx")
    private Animal animal;

    private String nickname;
    private String contents;
    private Integer like;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
