package com.git.backend.daeng_nyang_connect.tips.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tips_image")
public class TipsImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_idx")
    private Long tipsImageId;

    @ManyToOne
    @JoinColumn(name = "tips_board_idx")
    private Tips tips;

    private String url;
}
