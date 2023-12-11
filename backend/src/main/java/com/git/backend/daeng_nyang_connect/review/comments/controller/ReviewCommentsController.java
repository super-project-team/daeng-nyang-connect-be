package com.git.backend.daeng_nyang_connect.review.comments.controller;

import com.git.backend.daeng_nyang_connect.review.comments.dto.request.ReviewCommentsRequestDTO;
import com.git.backend.daeng_nyang_connect.review.comments.dto.response.ReviewCommentsResponseDTO;
import com.git.backend.daeng_nyang_connect.review.comments.entity.ReviewComments;
import com.git.backend.daeng_nyang_connect.review.comments.service.ReviewCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review/comment")
@EnableCaching
public class ReviewCommentsController {
    //    CRUD : 후기에 대한 댓글 등록 * 삭제 * 정보 수정 * 조회
    private final ReviewCommentsService reviewCommentsService;
    @PostMapping("/add")
    public ResponseEntity<?> addCommentsOnReview(@RequestParam("reviewId") Long reviewId,
                                                 @RequestBody ReviewCommentsRequestDTO commentDTO,
                                                 @RequestHeader("access_token") String token){
        ReviewComments newComments = reviewCommentsService.addCommentsOnReview(reviewId, commentDTO, token);
        ReviewCommentsResponseDTO response = reviewCommentsService.response(newComments);
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCommentsOnReview(@RequestParam("reviewCommentsId") Long reviewCommentsId,
                                            @RequestHeader("access_token") String token){
        reviewCommentsService.deleteCommentsOnReview(reviewCommentsId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateCommentsOnReview(@RequestParam("reviewCommentsId") Long reviewCommentsId,
                                            @RequestBody ReviewCommentsRequestDTO commentDTO,
                                            @RequestHeader("access_token") String token){
        ReviewComments updateComments = reviewCommentsService.updateCommentsOnReview(reviewCommentsId, commentDTO, token);
        ReviewCommentsResponseDTO response = reviewCommentsService.response(updateComments);
        return ResponseEntity.status(200).body(response);
    }

    // 내가 원하는 리뷰의 댓글 목록 전체 출력
    @GetMapping()
    public ResponseEntity<?> findAllCommentsByReview(@RequestParam("reviewId") Long reviewId,
                                              @RequestHeader("access_token") String token){
        List<ReviewComments> reviewCommentList = reviewCommentsService.findAllCommentsByReview(reviewId);
        List<ReviewCommentsResponseDTO> responseList = reviewCommentsService.responseList(reviewCommentList);
        return ResponseEntity.status(200).body(responseList);
    }

    @Transactional
    @PostMapping("/like")
    public ResponseEntity<?> likeCommentsOnReview(@RequestParam("reviewCommentsId") Long reviewCommentsId,
                                          @RequestHeader("access_token") String token){
        Map<String, String> message = reviewCommentsService.likeCommentsOnReview(reviewCommentsId, token);
        return ResponseEntity.status(200).body(message);
    }
}
