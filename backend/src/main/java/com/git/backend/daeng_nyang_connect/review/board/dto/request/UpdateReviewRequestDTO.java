package com.git.backend.daeng_nyang_connect.review.board.dto.request;

import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequestDTO {
    private String textReview;
    private List<ReviewImage> images;

    public void checkUpdateList(UpdateReviewRequestDTO updateAnimalRequestDTO, Review animal) {
        if (Objects.isNull(updateAnimalRequestDTO.getTextReview())) {
            this.textReview = animal.getTextReview();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getImages())) {
            this.images = animal.getImages();
        }
    }
}
