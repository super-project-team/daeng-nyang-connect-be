package com.git.backend.daeng_nyang_connect.oauth.controller;

import com.git.backend.daeng_nyang_connect.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OAuthService oAuthService;

    @RequestMapping("/naver_redirect")
    public ResponseEntity<?> naverLogin(HttpServletRequest request, HttpServletResponse response) {

        return oAuthService.naverLogin(request, response);
    }

}


