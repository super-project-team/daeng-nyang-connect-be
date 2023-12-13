package com.git.backend.daeng_nyang_connect.tips.comments.controller;

import com.git.backend.daeng_nyang_connect.tips.comments.dto.TipsCommentsDto;
import com.git.backend.daeng_nyang_connect.tips.comments.service.TipsCommentsService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
@Cacheable
@RequestMapping("/api/tips/comments")
public class TipsCommentsController {

    private final TipsCommentsService tipsCommentsService;

    @PostMapping("/post")
    public ResponseEntity<?> postComments(@RequestParam("id")Long tipsId,
                                       @RequestHeader("access_token")String token,
                                       @RequestBody TipsCommentsDto tipsCommentsDto){

        return tipsCommentsService.postComment(token, tipsId, tipsCommentsDto);
    }

    @PutMapping("/modify")
    public Map<?,?>modifyComments(@RequestHeader("access_token")String token,
                                  @RequestParam("id")Long tipsCommetnsId,
                                  @RequestBody TipsCommentsDto tipsCommentsDto){

        return tipsCommentsService.modifyComment(token, tipsCommetnsId, tipsCommentsDto);
    }

    @DeleteMapping("/delete")
    public Map<?,?>deleteComment(@RequestHeader("access_token")String token,
                                  @RequestParam("id")Long tipsCommetnsId){

        return tipsCommentsService.deleteComment(token, tipsCommetnsId);
    }

    @PostMapping("/like")
    public Map<?,?>like(@RequestHeader("access_token")String token,
                        @RequestParam("id")Long tipsCommentsId){

        return tipsCommentsService.clickLike(tipsCommentsId, token);
    }
}
