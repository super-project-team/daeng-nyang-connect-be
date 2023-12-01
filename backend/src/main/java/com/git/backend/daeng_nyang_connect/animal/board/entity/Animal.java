package com.git.backend.daeng_nyang_connect.animal.board.entity;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_board_idx")
    private Long animalId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String nickname;

    @Column(name = "animal_name")
    private String animalName;

    private Integer age;
    private String gender;
    private String disease;

    @Column(name = "adoption_date")
    private Date adoptionDate;

    private String training;

    private String neutering;
    private String contents;

    @Column(name = "health_check")
    private String healthCheck;

    private Integer like;

    @Column(name = "adoption_status")
    private Boolean adoptionStatus;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalImage> images;
}
