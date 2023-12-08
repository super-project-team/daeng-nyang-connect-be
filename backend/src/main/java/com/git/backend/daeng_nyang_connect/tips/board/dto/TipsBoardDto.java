package com.git.backend.daeng_nyang_connect.tips.board.dto;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;


import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipsBoardDto {

    private Long tipsBoardId;
    private String category;
    private String title;
    private String text;
    private Integer tipsLike;
    private Timestamp createdAt;

    //게시글 조회시 유저 닉네임 필요
    private String  nickName;

    public static TipsBoardDto fromEntity(Tips tips,String userNickName){
        return TipsBoardDto.builder()
                .tipsBoardId(tips.getTipsBoardId())
                .category(tips.getCategory())
                .title(tips.getTitle())
                .text(tips.getText())
                .nickName(userNickName)
                .createdAt(tips.getCreatedAt())
                .tipsLike(tips.getTipsLike())
                .build();
    }

}
