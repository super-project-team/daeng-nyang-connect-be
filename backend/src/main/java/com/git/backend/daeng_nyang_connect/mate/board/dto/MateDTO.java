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
    private Long userId;
    private String nickname;
    private String category;
    private String place;
    private String text;
    private Timestamp createdAt;
    private Integer mateLike;

    public static MateDTO fromMateEntity(Mate mate, String nickname) {
        return MateDTO.builder()
                .mateBoardId(mate.getMateBoardId())
                .category(mate.getCategory())
                .userId(mate.getUser().getUserId())
                .nickname(nickname)
                .place(mate.getPlace())
                .text(mate.getText())
                .createdAt(mate.getCreatedAt())
                .mateLike(mate.getMateLike())
                .build();
    }
}
