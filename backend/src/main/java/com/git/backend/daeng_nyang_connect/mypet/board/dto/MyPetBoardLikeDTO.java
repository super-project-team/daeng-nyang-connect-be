package com.git.backend.daeng_nyang_connect.mypet.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPetBoardLikeDTO {
    private Long myPetBoardLikeId;
    private Long userId;
}
