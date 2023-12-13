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
    private String breed;
    private Date lostDate;
    private String text;
    private List<LostImage> images;

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
