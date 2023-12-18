package com.git.backend.daeng_nyang_connect.mate.comments.controller;

import com.git.backend.daeng_nyang_connect.mate.comments.dto.MateCommentsDTO;
import com.git.backend.daeng_nyang_connect.mate.comments.service.MateCommentsService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/mate/comments")
public class MateCommentsController {

    private final MateCommentsService mateCommentsService;

    @PostMapping("/post")
    public Map<?, ?> postComment(@RequestHeader("access_token") String token,
                                 @RequestParam("id")Long mate,
                                 @RequestBody MateCommentsDTO mateCommentsDTO){
        return mateCommentsService.postComment(token, mate, mateCommentsDTO);
    }

    @PutMapping("/modify")
    public Map<?, ?> modifyComment(@RequestHeader("access_token") String token,
                                   @RequestParam("id")Long mateCommentsId,
                                   @RequestBody MateCommentsDTO mateCommentsDTO){
        return mateCommentsService.modifyComment(token, mateCommentsId, mateCommentsDTO);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteComment(@RequestHeader("access_token") String token,
                                   @RequestParam("id")Long mateCommentsId){
        return mateCommentsService.deleteComment(token, mateCommentsId);
    }

    @PostMapping("/like")
    public Map<?, ?> addLike(@RequestHeader("access_token") String token,
                             @RequestParam("id")Long mateCommentsId){
        return mateCommentsService.clickLike(mateCommentsId, token);
    }
}