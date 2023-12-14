package com.git.backend.daeng_nyang_connect.lost.board.dto;

import com.git.backend.daeng_nyang_connect.lost.board.entity.Lost;
import com.git.backend.daeng_nyang_connect.lost.board.entity.LostImage;
import com.git.backend.daeng_nyang_connect.lost.comments.dto.LostCommentsDTO;
import com.git.backend.daeng_nyang_connect.lost.comments.entity.LostComments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LostBoardDetailDTO {
    private Long lostBoardId;
    private String category;
    private String place;
    private String reward;
    private String phone;
    private String kind;
    private String breed;
    private String gender;
    private String color;
    private Date lostDate;
    private String lostTime;
    private String text;
    private Timestamp createdAt;
    private List<LostImage> images;

    private Long userId;
    private String nickname;
    private String userThumbnail;

//    private List<LostComments> lostCommentsId;
//    private List<LostComments> lostCommentsUserId;
//    private List<LostComments> lostCommentsNickname;
//    private List<LostComments> lostCommentsCreatedAt;
    private List<LostCommentsDTO> lostComments;

    public static LostBoardDetailDTO fromEntity
            (Lost lost, List<LostImage> lostImages,
             List<LostCommentsDTO> lostComments){
        return LostBoardDetailDTO.builder()
                .lostBoardId(lost.getLostBoardId())
                .category(lost.getCategory())
                .place(lost.getPlace())
                .reward(lost.getReward())
                .phone(lost.getPhone())
                .kind(lost.getKind())
                .breed(lost.getBreed())
                .gender(lost.getGender())
                .color(lost.getColor())
                .lostDate(lost.getLostDate())
                .lostTime(lost.getLostTime())
                .createdAt(lost.getCreatedAt())
                .text(lost.getText())
                .nickname(lost.getUser().getNickname())
                .images(lostImages)
                .lostComments(lostComments)
                .build();
    }
}
