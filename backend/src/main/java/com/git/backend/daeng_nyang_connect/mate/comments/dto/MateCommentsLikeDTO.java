package com.git.backend.daeng_nyang_connect.mate.comments.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateCommentsLikeDTO {
    private Long mateCommentsLikeId;
    private Long userId;
}
