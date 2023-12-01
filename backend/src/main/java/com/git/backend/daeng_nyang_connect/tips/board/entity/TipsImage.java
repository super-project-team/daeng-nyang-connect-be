package com.git.backend.daeng_nyang_connect.tips.board.entity;

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
@Table(name = "tips_image")
public class TipsImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_idx")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "tips_idx")
    private Tips tips;

    private String url;
}
