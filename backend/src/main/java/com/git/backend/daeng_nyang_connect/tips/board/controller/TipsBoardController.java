package com.git.backend.daeng_nyang_connect.tips.board.controller;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.repository.TipsBoardRepository;
import com.git.backend.daeng_nyang_connect.tips.board.repository.TipsImageRepository;
import com.git.backend.daeng_nyang_connect.tips.board.service.TipsBoardService;
import com.git.backend.daeng_nyang_connect.tips.board.service.TipsImgUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/tips")
public class TipsBoardController {

    private final TipsImageRepository tipsImageRepository;
    private final TipsBoardRepository tipsBoardRepository;
    private final TokenProvider tokenProvider;
    private final TipsBoardService tipsBoardService;
    private final TipsImgUpload tipsImgUpload;

    @PostMapping("/upload")
    public Map<?,?> upload(@RequestHeader("access_token")String token,
                             @RequestPart("data") TipsBoardDto tipsBoardDto,
                             @RequestPart("files") List<MultipartFile> fileList){
       return tipsBoardService.postBoard(tipsBoardDto, token, fileList);


    }

    @PostMapping("/like")
    public ResponseEntity<String > addLike(@RequestHeader("access_token")String token,
                                           @RequestParam("tipsId") Long tipsID){

        return tipsBoardService.clickLike(tipsID, token);
    }


}
