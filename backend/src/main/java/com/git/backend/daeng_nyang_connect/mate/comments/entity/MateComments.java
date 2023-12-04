package com.git.backend.daeng_nyang_connect.mate.comments.entity;

import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
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
@Table(name = "mate_comments")
public class MateComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mate_comments_idx")
    private Long mateCommentsId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mate_board_idx")
    private Mate mate;

    private String comment;
    private Integer like;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
