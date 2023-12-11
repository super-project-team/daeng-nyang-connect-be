package com.git.backend.daeng_nyang_connect.review.board.controller;


import com.git.backend.daeng_nyang_connect.animal.dto.response.AnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.dto.response.ReviewResponseDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@EnableCaching
public class ReviewController {
    //    CRUD : 입양 해온 동물에 대한 리뷰 등록 * 삭제 * 정보 수정 * 조회
    private final ReviewService reviewService;
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestParam("animalId") Long animalId,
                                       @RequestPart("dto") ReviewRequestDTO reviewRequestDTO,
                                       @RequestPart("files") List<MultipartFile> files,
                                       @RequestHeader("access_token") String token){
        Review newReview = reviewService.addReview(animalId, reviewRequestDTO, files, token);
        ReviewResponseDTO response = reviewService.response(newReview);
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(@RequestParam("commentsId") Long reviewId,
                                            @RequestHeader("access_token") String token){
        reviewService.deleteReview(reviewId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestParam("reviewId") Long reviewId,
                                          @RequestPart("dto") ReviewRequestDTO reviewRequestDTO,
                                          @RequestPart("files") List<MultipartFile> files,
                                          @RequestHeader("access_token") String token){
        Review updateReview = reviewService.updateReview(reviewId, reviewRequestDTO, files, token);
        ReviewResponseDTO response = reviewService.response(updateReview);
        return ResponseEntity.status(200).body(response);
    }

    // 입양간 동물에 대한 리뷰 전체 출력
    @GetMapping("/all")
    public ResponseEntity<?> findAllReview(@RequestHeader("access_token") String token){
        List<Review> reviewList = reviewService.findAllReview();
        List<ReviewResponseDTO> responseList = reviewService.responseList(reviewList);
        return ResponseEntity.status(200).body(responseList);
    }

    // 원하는 입양간 동물에 대한 리뷰 전체 출력
    @GetMapping()
    public ResponseEntity<?> findAllReviewByAnimalId(@RequestParam("animalId") Long animalId,
                                              @RequestHeader("access_token") String token){
        List<Review> reviewList = reviewService.findAllReviewByAnimal(animalId);
        List<ReviewResponseDTO> responseList = reviewService.responseList(reviewList);
        return ResponseEntity.status(200).body(responseList);
    }
    @Transactional
    @PostMapping("/like")
    public ResponseEntity<?> likeReview(@RequestParam("reviewId") Long reviewId,
                                          @RequestHeader("access_token") String token){
        Map<String, String> message = reviewService.likeReview(reviewId, token);
        return ResponseEntity.status(200).body(message);
    }
}
