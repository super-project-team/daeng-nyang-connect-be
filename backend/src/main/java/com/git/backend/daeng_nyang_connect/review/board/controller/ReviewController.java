package com.git.backend.daeng_nyang_connect.review.board.controller;


import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.UpdateReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal/comments")
public class ReviewController {
    //    CRUD : 파양동물 댓글 등록 * 삭제 * 정보 수정 * 조회
    private final ReviewService reviewService;
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestParam("adoptedAnimalId") Long adoptedAnimalId,
                                         @RequestBody ReviewRequestDTO reviewRequestDTO,
                                         @RequestHeader("X-AUTH-TOKEN") String token){
        Review newReview = reviewService.addReview(adoptedAnimalId, reviewRequestDTO, token);
        return ResponseEntity.status(200).body(newReview);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(@RequestParam("commentsId") Long reviewId,
                                            @RequestHeader("X-AUTH-TOKEN") String token){
        reviewService.deleteReview(reviewId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestParam("commentsId") Long commentsId,
                                            @RequestBody UpdateReviewRequestDTO updateReviewRequestDTO,
                                            @RequestHeader("X-AUTH-TOKEN") String token){
        Review updateReview = reviewService.updateReview(commentsId, updateReviewRequestDTO, token);
        return ResponseEntity.status(200).body(updateReview);
    }

    @GetMapping("/all")
    public List<Review> findAllReview(@RequestHeader("X-AUTH-TOKEN") String token){
        return reviewService.findAllReview();
    }

    @GetMapping()
    public List<Review> findAllReviewToAnimal(@RequestParam("animalId") Long animalId,
                                              @RequestHeader("X-AUTH-TOKEN") String token){
        return reviewService.findAllReviewToAnimal(animalId);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeReview(@RequestParam("reviewId") Long reviewId,
                                          @RequestHeader("X-AUTH-TOKEN") String token){
        Map<String, String> message = reviewService.likeReview(reviewId, token);
        return ResponseEntity.status(200).body(message);
    }
}
