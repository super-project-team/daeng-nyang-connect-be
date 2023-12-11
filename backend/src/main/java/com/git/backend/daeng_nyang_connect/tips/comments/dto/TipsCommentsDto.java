package com.git.backend.daeng_nyang_connect.tips.comments.dto;

import com.git.backend.daeng_nyang_connect.tips.comments.entity.TipsCommentsLike;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipsCommentsDto {

    private Long tipsCommentsId;
    private Long tipsId;
    private Long userId;
    private String comment;
    private Timestamp createdAt;
    private Integer tipsCommentLike;
    private String nickName;


    private List<TipsCommentsLike> commentsLikes;

}