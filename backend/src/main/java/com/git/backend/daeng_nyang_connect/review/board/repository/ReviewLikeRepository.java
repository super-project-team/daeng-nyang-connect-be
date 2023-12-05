package com.git.backend.daeng_nyang_connect.review.board.repository;

import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

//    좋아요 수 합산
    @Query("SELECT count(al) AS totalLike FROM ReviewLike al WHERE al.review.reviewId =: reviewId")
    Integer totalReviewLike(@Param("reviewId")Long reviewId);
}
