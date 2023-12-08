package com.git.backend.daeng_nyang_connect.mate.board.dto;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateDTO {

    private Long mateBoardId;
    private User userId;
    private String category;
    private String place;
    private String text;
    private Timestamp createdAt;
    private Integer mateLike;

}
