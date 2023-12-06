package com.git.backend.daeng_nyang_connect.tips.board.dto;

import lombok.*;

import java.sql.Timestamp;

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
    private Integer like;
    private Timestamp createdAt;

}
