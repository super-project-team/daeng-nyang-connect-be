package com.git.backend.daeng_nyang_connect.tips.board.dto;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsImage;
import com.git.backend.daeng_nyang_connect.tips.comments.entity.TipsComments;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipsBoardDetailDto {

    private Long tipsBoardId;
    private String category;
    private String title;
    private String text;
    private List<TipsImage> img;
    private Integer tipsLike;
    private Timestamp createdAt;

    //게시글 작성 유저 닉네임 필요
    private String nickName;

    private List<TipsComments> tipsCommentsId;
    private List<TipsComments> tipsCommentsNickname;
    private List<TipsComments> tipsCommentsCreatedAt;
    private List<TipsComments> tipsCommentsLike;

    public static TipsBoardDetailDto fromEntity(Tips tips, String boardNickname, List<TipsImage> tipsImage, List<TipsComments> tipsComments){
        return TipsBoardDetailDto.builder()
                .tipsBoardId(tips.getTipsBoardId())
                .category(tips.getCategory())
                .title(tips.getTitle())
                .text(tips.getText())
                .img(tipsImage)
                .tipsLike(tips.getTipsLike())
                .createdAt(tips.getCreatedAt())
                .nickName(boardNickname)
                .tipsCommentsId(tipsComments)
                .tipsCommentsNickname(tipsComments)
                .tipsCommentsCreatedAt(tipsComments)
                .tipsCommentsLike(tipsComments)
                .build();
    }


}
