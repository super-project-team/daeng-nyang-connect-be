package com.git.backend.daeng_nyang_connect.review.comments.dto.response;

import com.git.backend.daeng_nyang_connect.review.comments.entity.ReviewComments;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCommentsResponseDTO {
    private Long reviewId;
    private String userNickname;
    private String adoptedAnimalName;
    private String textReview;
    private String comment;
    private Timestamp createdAt;

    public ReviewCommentsResponseDTO(ReviewComments reviewComments) {
        this.reviewId = reviewComments.getReview().getReviewId();
        this.userNickname = reviewComments.getUser().getNickname();
        this.adoptedAnimalName = reviewComments.getReview().getAdoptedAnimal().getAnimal().getAnimalName();
        this.textReview = reviewComments.getReview().getTextReview();
        this.comment = reviewComments.getComment();
        this.createdAt = reviewComments.getCreatedAt();
    }
}
