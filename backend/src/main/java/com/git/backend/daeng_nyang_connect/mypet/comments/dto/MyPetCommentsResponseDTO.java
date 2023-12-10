package com.git.backend.daeng_nyang_connect.mypet.comments.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPetCommentsResponseDTO {
    private Long myPetCommentsId;
    private Long userId;
    private String nickname;
    private String comment;
    private Timestamp createdAt;
    private Integer myPetCommentsLike;
    private List<MyPetCommentsLikeDTO> myPetCommentsLikes;
}
