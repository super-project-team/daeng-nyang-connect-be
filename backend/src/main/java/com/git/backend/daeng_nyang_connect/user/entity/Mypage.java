package com.git.backend.daeng_nyang_connect.user.entity;

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
@Table(name = "mypage")
public class Mypage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_page_idx")
    private Long myPageId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx", unique = true)
    private User user;

    private String city;
    private String town;
    private String gender;
    private String mobile;
    private String experience;
    private String info;
}
