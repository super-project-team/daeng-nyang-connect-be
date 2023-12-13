package com.git.backend.daeng_nyang_connect.mypet.comments.controller;

import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsDTO;
import com.git.backend.daeng_nyang_connect.mypet.comments.service.MyPetCommentsService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/my_pet/comments")
public class MyPetCommentsController {

    private final MyPetCommentsService myPetCommentsService;

    @PostMapping("/post")
    public Map<?, ?> uploadComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPet,
                                           @RequestBody MyPetCommentsDTO myPetCommentsDTO){
        return myPetCommentsService.uploadComment(token, myPet, myPetCommentsDTO);
    }

    @PutMapping("/modify")
    public Map<?, ?> updateComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPetCommentsId,
                                           @RequestBody MyPetCommentsDTO myPetCommentsDTO){
        return myPetCommentsService.updateComment(token, myPetCommentsId, myPetCommentsDTO);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPetCommentsId){
        return myPetCommentsService.deleteComment(token, myPetCommentsId);
    }

    @PostMapping("/like")
    public Map<?, ?> addLike(@RequestHeader("access_token") String token,
                             @RequestParam("id")Long myPetCommentsId){
        return myPetCommentsService.clickLike(myPetCommentsId, token);
    }

}
