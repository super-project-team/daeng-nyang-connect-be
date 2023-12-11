package com.git.backend.daeng_nyang_connect.mate.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMateDTO {
    private Long mateBoardId;
    private String category;
    private String place;
    private String text;
}