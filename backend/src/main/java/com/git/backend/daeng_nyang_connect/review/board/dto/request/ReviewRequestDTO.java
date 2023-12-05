package com.git.backend.daeng_nyang_connect.review.board.dto.request;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
    private String textReview;
    private List<ReviewImage> images;

    public static Review addToEntity(ReviewRequestDTO reviewRequestDTO, User user, AdoptedAnimal adoptedAnimal ,Timestamp createdAt) {
        return Review.builder()
                    .user(user)
                    .adoptedAnimal(adoptedAnimal)
                    .textReview(reviewRequestDTO.getTextReview())
                    .like(0)
                    .createdAt(createdAt)
                    .build();
    }

    public static Review updateToDTO(UpdateReviewRequestDTO updateReviewRequestDTO, Review review) {
        return Review.builder()
                .user(review.getUser())
                .adoptedAnimal(review.getAdoptedAnimal())
                .textReview(updateReviewRequestDTO.getTextReview())
                .like(review.getLike())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
