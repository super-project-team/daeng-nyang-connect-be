package com.git.backend.daeng_nyang_connect.review.board.service;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.UpdateReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewImageRepository;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final AnimalRepository animalRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    @Override
    public Review addReview(Long adoptedAnimalId, ReviewRequestDTO reviewRequestDTO, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 해당 유저가 입양한 댕냥이가 맞는지 확인


        // 2. 후기를 DB에 저장
        Review newReview = ReviewRequestDTO.addToEntity(reviewRequestDTO, user, nowDate());
        reviewRepository.save(newReview);

        // 3. 이미지 url 변환
        if(!reviewRequestDTO.getImages().isEmpty()) {

        }

        // 4. 댕냥이 이미지 DB에 저장
        ReviewImage reviewImage = ReviewImage.builder()
                                            .review(newReview)
                                            .url(url)
                                            .build();
        reviewImageRepository.save(reviewImage);

        // 4. return 새로운 후기
        return newReview;
    }

    @Override
    public void deleteReview(Long reviewId, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 내가 작성한 글이 맞는지 확인
        Review myAnimal = checkMyReview(reviewId, user);

        // 3. 해당 댕냥이 게시글을 DB에서 삭제
        reviewRepository.delete(myAnimal);
    }

    @Override
    public Review updateReview(Long reviewId, UpdateReviewRequestDTO updateReviewRequestDTO, String token) {
        // 1. 토큰으로 유저 확인


        // 2. 내가 작성한 후기가 맞는지 확인
        Review myReview = checkMyReview(reviewId, user);

        // 3. 댕냥이 후기 정보를 DB에서 수정
        updateReviewRequestDTO.checkUpdateList(updateReviewRequestDTO, myReview);
        Review updateReview = ReviewRequestDTO.updateToDTO(updateReviewRequestDTO, myReview);
        reviewRepository.save(updateReview);

        // 4. 수정된 댕냥이 후기를 반환
        return updateReview;
    }

    @Override
    public List<Review> findAllReview() {
        // 후기 전체 보기
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAllReviewToAnimal(Long animalId) {
        // 해당 댕냥이에 대한 후기 찾기
        return reviewRepository.findReviewByAnimalId(animalId);
    }

    @Override
    public Map<String, String> likeReview(Long reviewId, String token) {
        //        1. 토큰으로 유저 확인

//        2. 해당 유저가 해당 댕냥이에게 좋아요를 눌렀는지 확인
        Animal animal = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        Map<String,String> message = new HashMap<>();

        if(animalLikeRepository.findByUser(user).isEmpty()){
            AnimalLike addLike = AnimalLike.builder()
                    .animal(animal)
                    .user(user)
                    .build();
            animal.updateLike(animalLikeRepository.totalAnimalLike(animalId));
            animalRepository.save(animal);

            animalLikeRepository.save(addLike);
            message.put("message", "좋아요가 성공적으로 추가되었습니다.");
            return message;
        }

        animalLikeRepository.deleteByUser(user);
        message.put("message", "좋아요가 성공적으로 삭제되었습니다.");
        return message;
    }

    @Override
    public Review checkMyReview(Long reviewId, User user) {
        // 1. 게시글 존재 유무 확인
        Review myReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("없는 후기입니다.")
        );

        // 2. 내가 작성한 게시글이 맞는지 확인
        if(!myReview.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 후기 댓글입니다.");
        }
        // 3. 내가 작성한 게시글 반환
        return myReview;
    }

    @Override
    public Timestamp nowDate(){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.valueOf(currentDateTime);
    }

}
