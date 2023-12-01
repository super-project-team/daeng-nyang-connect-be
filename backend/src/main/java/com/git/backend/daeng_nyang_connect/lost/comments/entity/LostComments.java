package com.git.backend.daeng_nyang_connect.lost.comments.entity;

import com.git.backend.daeng_nyang_connect.lost.board.entity.Lost;
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
@Table(name = "lost_comments")
public class LostComments {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lost_comments_idx")
    private Long lostCommentsId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lost_board_idx")
    private Lost lost;

    private String nickname;
    private String contents;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
