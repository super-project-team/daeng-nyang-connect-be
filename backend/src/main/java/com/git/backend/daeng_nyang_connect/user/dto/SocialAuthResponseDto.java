package com.git.backend.daeng_nyang_connect.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialAuthResponseDto {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String expires;
    private String scope;
    private String refreshExpires;
}
