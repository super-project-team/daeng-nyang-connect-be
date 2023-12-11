package com.git.backend.daeng_nyang_connect.lost.board.dto;

import com.git.backend.daeng_nyang_connect.lost.board.entity.Lost;
import com.git.backend.daeng_nyang_connect.lost.board.entity.LostImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LostBoardDTO {
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
    private String thumbnailImage;
    private List<LostImage> images;
    private String nickname;

    public static LostBoardDTO fromEntity(Lost lost){
        return LostBoardDTO.builder()
                .lostBoardId(lost.getLostBoardId())
                .category(lost.getCategory())
                .place(lost.getPlace())
                .lostDate(lost.getLostDate())
                .breed(lost.getBreed())
                .text(lost.getText())
                .images(lost.getImages())
                .build();
    }
}
