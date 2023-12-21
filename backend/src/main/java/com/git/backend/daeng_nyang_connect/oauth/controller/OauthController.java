package com.git.backend.daeng_nyang_connect.oauth.controller;

import com.git.backend.daeng_nyang_connect.oauth.service.OAuthService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "OAuth API")
public class OauthController {

    private final OAuthService oAuthService;

    @RequestMapping("/naver_redirect")
    public ResponseEntity<?> naverLogin(HttpServletRequest request, HttpServletResponse response) {

        return oAuthService.naverLogin(request, response);
    }

    @RequestMapping("/kakao_redirect")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request, HttpServletResponse response) {
        return oAuthService.kakaoLogin(request, response);
    }

}


