package com.git.backend.daeng_nyang_connect.mate.board.dto;

import com.git.backend.daeng_nyang_connect.mate.board.entity.MateImage;
import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateComments;
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
    private String category;
    private List<MateImage> img;
    private String place;
    private Timestamp createdAt;
    private String text;
    private List<MateComments> comment;
    private Integer mateLike;

    private Long userId;
    private String nickname;
}
