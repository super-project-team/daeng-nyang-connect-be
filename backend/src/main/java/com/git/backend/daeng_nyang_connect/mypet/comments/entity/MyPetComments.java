package com.git.backend.daeng_nyang_connect.mypet.comments.entity;

import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
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
@Table(name = "my_pet_comments")
public class MyPetComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_pet_comments_idx")
    private Long myPetCommentsId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "my_pet_board_idx")
    private MyPet mypet;

    private String comment;
    private Integer like;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
