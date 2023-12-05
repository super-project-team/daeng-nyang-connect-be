package com.git.backend.daeng_nyang_connect.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalScrap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userId;

    private String name;
    private String email;
    private String password;
    private String nickname;
    private String city;
    private String town;
    private String gender;
    private String mobile;
    private Boolean experience;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MyPage myPage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AnimalScrap> myAnimalScrap;
}
