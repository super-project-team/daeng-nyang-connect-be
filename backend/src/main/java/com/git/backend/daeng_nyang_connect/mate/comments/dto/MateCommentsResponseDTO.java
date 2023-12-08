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
    private String comment;
    private Timestamp createdAt;
    private List<MateCommentsLikeDTO> mateCommentsLikes;

    private Long userId;
    private String nickname;
}

