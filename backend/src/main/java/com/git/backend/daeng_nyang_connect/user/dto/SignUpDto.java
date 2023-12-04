package com.git.backend.daeng_nyang_connect.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private String mobile;
    private String nickname;
    private String city;
    private String town;
    private boolean experience;
    private char gender;

}
