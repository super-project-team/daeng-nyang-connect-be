package com.git.backend.daeng_nyang_connect.mate.comments.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateCommentsResponseDTO {
    private Long mateCommentsId;
    private Long userId;
    private String nickname;
    private String comment;
    private Timestamp createdAt;
    private Integer mateCommentsLike;
    private List<MateCommentsLikeDTO> mateCommentsLikes;
}

