package com.git.backend.daeng_nyang_connect.mypet.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPetDTO {

    private Long myPetBoardId;
    private Long userId;
    private String nickname;
    private String kind;
    private String breed;
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    private Integer myPetLike;

    public static MyPetDTO fromMyPetEntity(MyPet myPet, String nickname) {
        return MyPetDTO.builder()
                .myPetBoardId(myPet.getMyPetBoardId())
                .userId(myPet.getUser().getUserId())
                .nickname(nickname)
                .kind(myPet.getKind())
                .breed(myPet.getBreed())
                .text(myPet.getText())
                .createdAt(myPet.getCreatedAt())
                .myPetLike(myPet.getMyPetLike())
                .build();
    }

}
