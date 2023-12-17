package com.git.backend.daeng_nyang_connect.user.controller;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.dto.FindDto;
import com.git.backend.daeng_nyang_connect.user.dto.LoginDto;
import com.git.backend.daeng_nyang_connect.user.dto.SignUpDto;
import com.git.backend.daeng_nyang_connect.user.service.UserService;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/IdCheck")
    public ResponseEntity<?> IdCheck(@RequestBody SignUpDto signUpDto){
        return userService.checkUserId(signUpDto.getEmail());
    }

    @GetMapping("/NicknameCheck")
    public ResponseEntity<?> NicknameCheck(@RequestBody SignUpDto signUpDto){
        return userService.checkUserNickname(signUpDto.getNickname());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        return userService.login(loginDto, httpServletResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("access_token") String token){
        userService.logout(token);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }

    @GetMapping("/findId")
    public ResponseEntity<?> findUserId(@RequestBody FindDto findDto){
        return userService.findUserId(findDto);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<?> findPassword(@RequestBody FindDto findDto){
        return userService.setNewPassword(findDto);
    }

}
