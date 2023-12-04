package com.git.backend.daeng_nyang_connect.lost.board.entity;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
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

    private String category;
    private String place;
    private String reward;
    private String kind;
    private String gender;
    private String color;

    @Column(name = "lost_date")
    private Date lostDate;

    @Column(name = "lost_time")
    private String lostTime;

    @Column(name = "lost_minute")
    private String lostMinute;

    private String text;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LostImage> images;
}
