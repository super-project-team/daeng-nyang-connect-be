package com.git.backend.daeng_nyang_connect.tips.comments.dto;

import lombok.*;

import java.sql.Timestamp;

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


}
