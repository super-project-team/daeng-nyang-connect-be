package com.git.backend.daeng_nyang_connect.mate.board.entity;

import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateComments;
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
@Table(name = "mate")
public class Mate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mate_board_idx")
    private Long mateBoardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String category;
    private String place;
    private String text;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "mate_like")
    private Integer mateLike;

    @Getter
    @OneToMany(mappedBy = "mate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MateImage> img;

    @Getter
    @OneToMany(mappedBy = "mate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MateComments> comment;

    public void setMateLike(Integer mateLike){
        this.mateLike = mateLike;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
