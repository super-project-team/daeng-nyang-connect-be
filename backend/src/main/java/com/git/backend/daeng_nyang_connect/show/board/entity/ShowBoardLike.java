package com.git.backend.daeng_nyang_connect.show.board.entity;

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
@Table(name = "show_board_like")
public class ShowBoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_board_like_idx")
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_board_idx")
    private Show show;
}
