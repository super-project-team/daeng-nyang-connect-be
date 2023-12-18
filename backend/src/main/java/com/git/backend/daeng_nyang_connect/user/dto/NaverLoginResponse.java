package com.git.backend.daeng_nyang_connect.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverLoginResponse {

    @Builder.Default
    private Response response = Response.builder().build();
    private String resultCode;
    private String message;



    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String id;
        private String nickName;
        private String profile_image;
        private String age;
        private String gender;
        private String email;
        private String mobile;
        private String name;
    }

}
