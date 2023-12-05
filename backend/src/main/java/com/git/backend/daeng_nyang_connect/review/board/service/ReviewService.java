package com.git.backend.daeng_nyang_connect.review.board.service;


import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.UpdateReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.user.entity.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ReviewService {
    // 입양 후기 작성
    Review addReview(Long adoptedAnimalId, ReviewRequestDTO reviewRequestDTO, String token);

    // 입양 후기 삭제
    void deleteReview(Long reviewId, String token);

    // 입양 후기 수정
    Review updateReview(Long commentsId, UpdateReviewRequestDTO updateReviewRequestDTO, String token);

    // 해당 파양동물의 입양 후기 출력
    List<Review> findAllReviewToAnimal(Long adoptedAnimalId);

    List<Review> findAllReview();

    // 해당 후기에 좋아요 클릭 - message 반환
    Map<String, String> likeReview(Long reviewId, String token);

    // 내가 작성한 후기인지 확인
    Review checkMyReview(Long reviewId, User user);

    Timestamp nowDate();
}
