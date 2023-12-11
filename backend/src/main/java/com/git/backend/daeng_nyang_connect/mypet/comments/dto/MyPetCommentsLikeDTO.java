package com.git.backend.daeng_nyang_connect.mypet.comments.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPetCommentsLikeDTO {
    private Long myPetCommentsLikeId;
    private Long userId;
}
