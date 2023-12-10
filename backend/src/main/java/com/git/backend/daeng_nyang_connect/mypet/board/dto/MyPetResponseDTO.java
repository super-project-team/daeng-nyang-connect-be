package com.git.backend.daeng_nyang_connect.mypet.board.dto;

import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsResponseDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPetResponseDTO {
    private Long myPetBoardId;
    private Long userId;
    private String nickname;
    private String kind;
    private String breed;
    private String text;
    private Timestamp createdAt;
    private List<String> img;
    private List<MyPetCommentsResponseDTO> comments;
    private Integer myPetLike;
    private List<MyPetBoardLikeDTO> myPetLikes;

}
