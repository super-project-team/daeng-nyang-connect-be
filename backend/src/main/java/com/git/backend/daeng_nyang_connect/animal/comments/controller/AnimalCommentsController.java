package com.git.backend.daeng_nyang_connect.animal.comments.controller;

import com.git.backend.daeng_nyang_connect.animal.comments.entity.AnimalComments;
import com.git.backend.daeng_nyang_connect.animal.comments.service.AnimalCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal/comments")
public class AnimalCommentsController {
//    CRUD : 파양동물 댓글 등록 * 삭제 * 정보 수정 * 조회
    private final AnimalCommentsService animalCommentsService;
    @PostMapping("/add")
    public ResponseEntity<?> addComments(@RequestParam("animalId") Long animalId,
                                         @RequestBody String comments,
                                         @RequestHeader("X-AUTH-TOKEN") String token){
        AnimalComments newComments = animalCommentsService.addComments(animalId, comments, token);
        return ResponseEntity.status(200).body(newComments);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComments(@RequestParam("commentsId") Long commentsId,
                                            @RequestHeader("X-AUTH-TOKEN") String token){
        animalCommentsService.deleteComments(commentsId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateComments(@RequestParam("commentsId") Long commentsId,
                                            @RequestBody String comments,
                                            @RequestHeader("X-AUTH-TOKEN") String token){
        AnimalComments updateComments = animalCommentsService.updateComments(commentsId, comments, token);
        return ResponseEntity.status(200).body(updateComments);
    }

    @GetMapping("/all")
    public List<AnimalComments> findAllCommentsToAnimal(@RequestParam("animalId") Long animalId,
                                                        @RequestHeader("X-AUTH-TOKEN") String token){
        return animalCommentsService.findAllCommentsToAnimal(animalId);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeComments(@RequestParam("commentsId") Long commentsId,
                                        @RequestHeader("X-AUTH-TOKEN") String token){
        Map<String, String> message = animalCommentsService.likeComments(commentsId, token);
        return ResponseEntity.status(200).body(message);
    }
}
