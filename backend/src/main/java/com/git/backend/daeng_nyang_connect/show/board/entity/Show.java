package com.git.backend.daeng_nyang_connect.show.board.entity;

import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalImage;
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
@Table(name = "show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_board_idx")
    private Long showBoardId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String nickname;
    private String title;
    private String contents;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowImage> images;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private Integer like;
}
