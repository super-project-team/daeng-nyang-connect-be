package com.git.backend.daeng_nyang_connect.mypet.board.entity;

import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_pet_image")
public class MyPetImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mypet_image_idx")
    private Long myPetImageId;

    @ManyToOne
    @JoinColumn(name = "my_pet_board_idx")
    private MyPet mypet;

    private String url;
}
