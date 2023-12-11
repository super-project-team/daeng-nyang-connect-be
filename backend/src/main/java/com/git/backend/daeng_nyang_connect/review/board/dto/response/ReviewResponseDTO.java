package com.git.backend.daeng_nyang_connect.review.board.dto.response;

import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private String userNickname;
    private String adoptedAnimalName;
    private String textReview;
    private Timestamp createdAt;
    private List<String> images = new ArrayList<>();

    public ReviewResponseDTO(Review review, List<ReviewImage> reviewImages) {
        this.userNickname = review.getUser().getNickname();
        this.adoptedAnimalName = review.getAdoptedAnimal().getAnimal().getAnimalName();
        this.textReview = review.getTextReview();
        this.createdAt = review.getCreatedAt();

        for(ReviewImage reviewImage : reviewImages){
            this.images.add(reviewImage.getUrl());
        }
    }
}
