package com.git.backend.daeng_nyang_connect.tips.board.controller;


import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDetailDto;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.service.TipsBoardService;

import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
@Cacheable
@RequestMapping("/api/tips")
public class TipsBoardController {

    private final TipsBoardService tipsBoardService;


    @PostMapping("/post")
    public Map<?,?> upload(@RequestHeader("access_token")String token,
                            TipsBoardDto tipsBoardDto,
                            List<MultipartFile> files){
       return tipsBoardService.postBoard(tipsBoardDto, token, files);

    }

    @PostMapping("/like")
    public ResponseEntity<String > addLike(@RequestHeader("access_token")String token,
                                           @RequestParam("tipsId") Long tipsID){
        return tipsBoardService.clickLike(tipsID, token);
    }

    @DeleteMapping("/delete")
    public Map<String ,String> delete(@RequestHeader("access_token")String token,
                                    @RequestParam("tipsId")Long tipsId){
       return tipsBoardService.delete(token, tipsId);
    }

    @PutMapping("/modify")
    public Map<String ,String> modify(@RequestHeader("access_token")String token,
                                      @RequestParam("tipsId")Long tipsId,
                                      @RequestParam("tipsImgId")Long tipsImgId,
                                      TipsBoardDto tipsBoardDto,
                                      MultipartFile files) throws FileUploadFailedException {
        return tipsBoardService.modifyTips(token,tipsId,tipsBoardDto, tipsImgId, files);
    }

    @GetMapping("/getAll")
    public List<TipsBoardDto> getAll(Pageable pageable){
       return tipsBoardService.getAll(pageable);
    }


    @GetMapping("/getBoard")
    public TipsBoardDetailDto getThisBoard(@RequestParam("id")Long tipsId){
        return tipsBoardService.getThisBoard(tipsId);
    }

    @GetMapping("/search")
    public List<TipsBoardDto> searchBoard(@RequestParam String keyword){
        return tipsBoardService.searchBoard(keyword);
    }
    @GetMapping("/getSize")
    public Map<String ,Integer> getSize(){
        return tipsBoardService.getSize();
    }

}
