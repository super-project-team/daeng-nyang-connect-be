package com.git.backend.daeng_nyang_connect.review.board.dto.response;

import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long reviewId;
    private String userNickname;
    private String adoptedAnimalName;
    private String textReview;
    private String createdAt;
    private Integer age;
    private String userThumbnail;
    private List<String> images = new ArrayList<>();

    public ReviewResponseDTO(Review review, List<ReviewImage> reviewImages) {
        this.reviewId = review.getReviewId();
        this.userNickname = review.getUser().getNickname();
        this.adoptedAnimalName = review.getAdoptedAnimal().getAnimal().getAnimalName();
        this.textReview = review.getTextReview();
        this.createdAt = TimestampToFormattedString(review.getCreatedAt());
        this.age = review.getAdoptedAnimal().getAnimal().getAge();
        this.userThumbnail = review.getUser().getMyPage().getImg();

        for(ReviewImage reviewImage : reviewImages){
            this.images.add(reviewImage.getUrl());
        }
    }

    public String TimestampToFormattedString(Timestamp time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return dateFormat.format(time);
    }
}
