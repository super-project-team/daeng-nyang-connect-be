package com.git.backend.daeng_nyang_connect.user.controller;

import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardLikeDto;
import com.git.backend.daeng_nyang_connect.user.dto.ModifyUserDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyBoardDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyPageDto;
import com.git.backend.daeng_nyang_connect.user.service.FindMyBoardService;
import com.git.backend.daeng_nyang_connect.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final FindMyBoardService findMyBoardService;


    @GetMapping("/get")
    public MyPageDto getMyPage(@RequestHeader("access_token")String token){
        return myPageService.getMyInfo(token);
    }

    @PutMapping ("/modifyProfile")
    private Map<String ,String> modifyProfile(@RequestHeader("access_token")String token,
                                              @RequestBody MultipartFile multipartFile) throws FileUploadFailedException {
        return  myPageService.modifyProfile(token, multipartFile);
    }

    @PutMapping("/modifyInfo")
    private Map<String ,String> modifyInfo(@RequestHeader("access_token")String token,
                                           @RequestBody ModifyUserDto modifyUserDto){
        return myPageService.modifyInfo(token, modifyUserDto);
    }

    @PutMapping("/modifyNickname")
    public Map<String ,String > modifyNickname(@RequestHeader("access_token")String token,
                                               @RequestBody ModifyUserDto modifyUserDto){
        return myPageService.modifyNickname(token, modifyUserDto);
    }

    @PutMapping("/modifyPassword")
    public Map<String,String > modifyPassword(@RequestHeader("access_token")String token,
                                              @RequestBody ModifyUserDto modifyUserDto){
        return myPageService.modifyPassword(token, modifyUserDto);
    }

    @GetMapping("/getMyBoard")
    public MyBoardDto getMyBoard(@RequestHeader("access_token") String token){
        return findMyBoardService.getMyBoard(token);
    }

    @GetMapping("/getMyLikeBoard")
    public List<Object>  getMyLikeBoard(@RequestHeader("access_token") String token){
        return findMyBoardService.getLikeBoard(token);
    }

}
