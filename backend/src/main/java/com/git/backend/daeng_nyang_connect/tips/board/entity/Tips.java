package com.git.backend.daeng_nyang_connect.tips.board.entity;

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
@Table(name = "tips")
public class Tips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tips_board_idx")
    private Long tipsBoardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String category;
    private String title;
    private String text;

    @Column(name = "tips_like")
    private Integer tips_like;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
