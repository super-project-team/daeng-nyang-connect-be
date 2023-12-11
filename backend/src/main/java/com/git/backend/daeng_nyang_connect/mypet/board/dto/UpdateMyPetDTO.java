package com.git.backend.daeng_nyang_connect.mypet.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMyPetDTO {
    private Long myPetBoardId;
    private String kind;
    private String breed;
    private String text;
}
