package com.git.backend.daeng_nyang_connect.mate.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String userThumbnail;
    private String category;
    private String place;
    private String text;
    private List<String> img;
    private List<Long> mateImgId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    private List<MateCommentsResponseDTO> comments;
    private List<MateBoardLikeDTO> mateLikes;

}
