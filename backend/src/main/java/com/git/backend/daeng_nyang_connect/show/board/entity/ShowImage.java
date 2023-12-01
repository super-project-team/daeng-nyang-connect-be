package com.git.backend.daeng_nyang_connect.show.board.entity;

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
@Table(name = "show_image")
public class ShowImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_image_idx")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "show_board_idx")
    private Show show;

    private String url;
}
