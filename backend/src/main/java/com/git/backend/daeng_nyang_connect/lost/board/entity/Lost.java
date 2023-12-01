package com.git.backend.daeng_nyang_connect.lost.board.entity;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lost")
public class Lost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lost_board_idx")
    private Long lostBoardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String title;
    private String contents;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LostImage> images;
}
