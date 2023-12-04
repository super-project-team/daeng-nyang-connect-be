package com.git.backend.daeng_nyang_connect.mypet.board.entity;

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
@Table(name = "my_pet")
public class MyPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_pet_board_idx")
    private Long myPetBoardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String kind;
    private String breed;
    private String text;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private Integer like;
}
