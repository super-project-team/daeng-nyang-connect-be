package com.git.backend.daeng_nyang_connect.review.comments.repository;

import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewLike;
import com.git.backend.daeng_nyang_connect.review.comments.entity.ReviewCommentsLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewCommentsLikeRepository extends JpaRepository<ReviewCommentsLike, Long> {

//    좋아요 수 합산
    @Query("SELECT count(al) AS totalLike FROM ReviewLike al WHERE al.review.reviewId =: reviewId")
    Integer totalReviewLike(@Param("reviewId")Long reviewId);

    Optional<ReviewLike> findByUser(User user);

    void deleteByUser(User user);
}
