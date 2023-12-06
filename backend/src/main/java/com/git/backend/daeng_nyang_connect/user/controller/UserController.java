package com.git.backend.daeng_nyang_connect.user.controller;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.dto.LoginDto;
import com.git.backend.daeng_nyang_connect.user.dto.SignUpDto;
import com.git.backend.daeng_nyang_connect.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String > redisTemplate;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto){return userService.signUp(signUpDto);}

    @PostMapping("/login")
    private Map<String, String >login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        return userService.login(loginDto, httpServletResponse);
    }


}
