package com.git.backend.daeng_nyang_connect.lost.board.entity;

import com.git.backend.daeng_nyang_connect.user.entity.User;
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
@Table(name = "lost_board_like")
public class LostBoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lost_board_like_idx")
    private Long lostBoardLikeId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lost_board_idx")
    private Lost lost;
}
