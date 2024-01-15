package com.git.backend.daeng_nyang_connect.admin.dto;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Long userId;
    private String email;
    private String name;
    private String mobile;
    private String nickname;

    public static UsersDto from(User user){
        return UsersDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .mobile(user.getMobile())
                .build();
    }
}
