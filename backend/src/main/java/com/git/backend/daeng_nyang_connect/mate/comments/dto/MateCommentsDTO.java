package com.git.backend.daeng_nyang_connect.mate.comments.dto;

import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateCommentsDTO {
    private Long mateCommentsId;
    private Long userId;
    private String nickName;
    private Mate mate;
    private String comment;
    private Timestamp createdAt;
    private Integer mateCommentsLike;
    private List<MateCommentsLikeDTO> mateCommentsLikes;
}