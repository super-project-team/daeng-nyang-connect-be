package com.git.backend.daeng_nyang_connect.mate.board.dto;

import com.git.backend.daeng_nyang_connect.mate.comments.dto.MateCommentsResponseDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateResponseDTO {

    private Long mateBoardId;
    private Long userId;
    private String nickname;
    private String category;
    private List<String> img;
    private String place;
    private Timestamp createdAt;
    private String text;
    private List<MateCommentsResponseDTO> comments;
    private Integer mateLike;
    private List<MateBoardLikeDTO> mateLikes;

}
