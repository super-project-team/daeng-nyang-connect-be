package com.git.backend.daeng_nyang_connect.mypet.comments.controller;

import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsDTO;
import com.git.backend.daeng_nyang_connect.mypet.comments.service.MyPetCommentsService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/my_pet/comments")
public class MyPetCommentsController {

    private final MyPetCommentsService myPetCommentsService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPet,
                                           @RequestBody MyPetCommentsDTO myPetCommentsDTO){
        return myPetCommentsService.uploadComment(token, myPet, myPetCommentsDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPetCommentsId,
                                           @RequestBody MyPetCommentsDTO myPetCommentsDTO){
        return myPetCommentsService.updateComment(token, myPetCommentsId, myPetCommentsDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestHeader("access_token") String token,
                                           @RequestParam("id")Long myPetCommentsId){
        return myPetCommentsService.deleteComment(token, myPetCommentsId);
    }

    @PostMapping("/like")
    public ResponseEntity<?> addLike(@RequestHeader("access_token") String token,
                                     @RequestParam("id")Long myPetCommentsId){
        return myPetCommentsService.clickLike(myPetCommentsId, token);
    }

}
