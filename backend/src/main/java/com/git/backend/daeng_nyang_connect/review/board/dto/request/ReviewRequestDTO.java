package com.git.backend.daeng_nyang_connect.review.board.dto.request;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
    private String textReview;
    private List<ReviewImage> images;

    public void checkUpdateList(ReviewRequestDTO updateAnimalRequestDTO, Review animal) {
        if (Objects.isNull(updateAnimalRequestDTO.getTextReview())) {
            this.textReview = animal.getTextReview();
        }
    }
}
