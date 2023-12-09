package com.git.backend.daeng_nyang_connect.lost.board.entity;

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
@Table(name = "lost_image")
public class LostImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lost_image_idx")
    private Long lostImageId;

    @ManyToOne
    @JoinColumn(name = "lost_idx")
    private Lost lost;

    private String url;
}
