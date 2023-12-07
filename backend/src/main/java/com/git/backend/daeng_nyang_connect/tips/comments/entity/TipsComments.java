package com.git.backend.daeng_nyang_connect.tips.comments.entity;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tips_comments")
public class TipsComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tips_comments_idx")
    private Long tipsCommentsId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tip_board_idx")
    private Tips tips;

    private String comment;

    private Integer tipsCommentsLike;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
