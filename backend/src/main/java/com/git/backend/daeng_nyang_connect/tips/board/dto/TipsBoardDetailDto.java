package com.git.backend.daeng_nyang_connect.tips.board.dto;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsImage;
import com.git.backend.daeng_nyang_connect.tips.comments.dto.TipsCommentsDto;
import com.git.backend.daeng_nyang_connect.tips.comments.entity.TipsComments;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
    private Timestamp boardCreatedAt;

    //게시글 작성 유저 닉네임 필요
    private String boardAuthor;

    private List<Long> tipsCommentsId;
    private List<String > comments;
    private List<String> commentsAuthor;
    private List<Timestamp> commentsCreatedAt;
    private List<Integer> commentsLike;

    public static TipsBoardDetailDto fromEntity(Tips tips, List<TipsImage> tipsImage, List<TipsCommentsDto> tipsComments) {
        return TipsBoardDetailDto.builder()
                .tipsBoardId(tips.getTipsBoardId())
                .category(tips.getCategory())
                .title(tips.getTitle())
                .text(tips.getText())
                .img(tipsImage)
                .tipsLike(tips.getTipsLike())
                .boardCreatedAt(tips.getCreatedAt())
                .boardAuthor(tips.getUser().getNickname())
                .tipsCommentsId(tipsComments.stream().map(TipsCommentsDto::getTipsCommentsId).collect(Collectors.toList()))
                .commentsAuthor(tipsComments.stream().map(TipsCommentsDto::getNickName).collect(Collectors.toList()))
                .comments(tipsComments.stream().map(TipsCommentsDto::getComment).collect(Collectors.toList()))
                .commentsCreatedAt(tipsComments.stream().map(TipsCommentsDto::getCreatedAt).collect(Collectors.toList()))
                .commentsLike(tipsComments.stream().map(TipsCommentsDto::getTipsCommentLike).collect(Collectors.toList()))
                .build();
    }


}
