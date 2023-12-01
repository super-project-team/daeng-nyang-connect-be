package com.git.backend.daeng_nyang_connect.show.comments.entity;

import com.git.backend.daeng_nyang_connect.show.board.entity.Show;
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
@Table(name = "show_board_comments")
public class ShowBoardComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_board_comments_idx")
    private Long commentsId;
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_board_idx")
    private Show show;
    private String contents;
    private Integer like;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
