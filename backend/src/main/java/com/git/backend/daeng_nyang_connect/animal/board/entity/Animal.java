package com.git.backend.daeng_nyang_connect.animal.board.entity;

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
@Table(name = "animal")
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_idx")
    private Long animalId;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @Column(name = "animal_name")
    private String animalName;

    private Integer age;
    private String gender;
    private String disease;
    private String breed;
    private String training;
    private Boolean neutering;
    private String contents;

    @Column(name = "health_check")
    private String healthCheck;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "nurture_period")
    private String nurturePeriod;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalImage> images;
}
