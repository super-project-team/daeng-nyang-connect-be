package com.git.backend.daeng_nyang_connect.review.board.service;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.repository.AdoptedAnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewImage;
import com.git.backend.daeng_nyang_connect.review.board.entity.ReviewLike;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewImageRepository;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewLikeRepository;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final AdoptedAnimalRepository adoptedAnimalRepository;
    private final TokenProvider tokenProvider;
    @Override
    public Review addReview(Long animalId, ReviewRequestDTO reviewRequestDTO, String token) {
        // 1. 토큰으로 유저 확인
        User user = checkUserByToken(token);

        // 2. 해당 유저가 입양한 댕냥이가 맞는지 확인
        AdoptedAnimal myAdoptedAnimal = checkMyAdoptedAnimal(animalId, user);

        // 3. 후기를 DB에 저장
        Review newReview = Review.builder()
                                .user(user)
                                .adoptedAnimal(myAdoptedAnimal)
                                .textReview(reviewRequestDTO.getTextReview())
                                .like(0)
                                .createdAt(nowDate())
                                .build();
        reviewRepository.save(newReview);

        // 4. 댕냥이 이미지 DB에 저장
        reviewRequestDTO.getImages().forEach(image -> uploadImage(newReview, image));

        // 6. return 새로운 후기
        return newReview;
    }

    @Override
    public void deleteReview(Long reviewId, String token) {
        // 1. 토큰으로 유저 확인
        User user = checkUserByToken(token);

        // 2. 내가 작성한 글이 맞는지 확인
        Review myAnimal = checkMyReview(reviewId, user);

        // 3. 해당 댕냥이 게시글을 DB에서 삭제
        reviewRepository.delete(myAnimal);
    }

    @Override
    public Review updateReview(Long reviewId, ReviewRequestDTO reviewRequestDTO, String token) {
        // 1. 토큰으로 유저 확인
        User user = checkUserByToken(token);

        // 2. 내가 작성한 후기가 맞는지 확인
        Review myReview = checkMyReview(reviewId, user);

        // 3. 댕냥이 후기 정보를 DB에서 수정
        reviewRequestDTO.checkUpdateList(reviewRequestDTO, myReview);

        Review updateReview = Review.builder()
                                    .user(myReview.getUser())
                                    .adoptedAnimal(myReview.getAdoptedAnimal())
                                    .textReview(reviewRequestDTO.getTextReview()) // text 수정
                                    .like(myReview.getLike())
                                    .createdAt(myReview.getCreatedAt())
                                    .build();
        reviewRepository.save(updateReview);

        // 4. 댕냥이 이미지 DB에 저장
        reviewRequestDTO.getImages().forEach(image -> uploadImage(updateReview, image));

        // 5. 수정된 댕냥이 후기를 반환
        return updateReview;
    }

    @Override
    public List<Review> findAllReview() {
        // 입양 후기 전체 보기
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAllReviewByAnimal(Long animalId) {
        // 해당 댕냥이에 대한 후기 찾기
        return reviewRepository.findReviewByAnimalId(animalId);
    }

    @Override
    public Map<String, String> likeReview(Long reviewId, String token) {
        // 1. 토큰으로 유저 확인
        User user = checkUserByToken(token);

        // 2. 해당 유저가 해당 댕냥이 후기에 좋아요를 눌렀는지 확인
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("없는 후기입니다.")
        );

        Map<String,String> message = new HashMap<>();

        if(reviewLikeRepository.findByUser(user).isPresent()){
            // 3. 만약 해당 댓글에 좋아요가 이미 눌러져 있다면 (좋아요 - 1)
            reviewLikeRepository.deleteByUser(user);

            updateLike(review, reviewLikeRepository.totalReviewLike(reviewId));


            message.put("message", "좋아요가 성공적으로 삭제되었습니다.");
            return message;
        }

        // 3. 해당 댓글에 좋아요를 처음 누를때 (좋아요 + 1)
        ReviewLike addLike = ReviewLike.builder()
                                    .review(review)
                                    .user(user)
                                    .build();
        reviewLikeRepository.save(addLike);


        updateLike(review, reviewLikeRepository.totalReviewLike(reviewId));

        message.put("message", "좋아요가 성공적으로 추가되었습니다.");
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
    public AdoptedAnimal checkMyAdoptedAnimal(Long animalId, User user) {
        // 1. 입양한 댕냥이 존재 유무 확인
        AdoptedAnimal adoptedAnimal = adoptedAnimalRepository.findByAnimalId(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 댕냥이입니다.")
        );

        // 2. 내가 입양한 댕냥이가 맞는지 확인
        if(!adoptedAnimal.getUser().equals(user)) {
            throw new IllegalArgumentException("회원님이 입양 한 동물이 아닙니다.");
        }

        // 3. 내가 작성한 게시글 반환
        return adoptedAnimal;
    }


    @Override
    public Timestamp nowDate(){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.valueOf(currentDateTime);
    }

    @Override
    public void updateLike(Review review, Integer like){
        Review totalLike = Review.builder()
                                .user(review.getUser())
                                .adoptedAnimal(review.getAdoptedAnimal())
                                .textReview(review.getTextReview())
                                .createdAt(review.getCreatedAt())
                                .like(like) // 좋아요만 수정
                                .build();
        reviewRepository.save(totalLike);
    }

    @Override
    public void uploadImage(Review review, ReviewImage image){
        ReviewImage reviewImage = ReviewImage.builder()
                                            .review(review)
                                            .url(image.getUrl())
                                            .build();
        reviewImageRepository.save(reviewImage);
    }

    @Override
    public User checkUserByToken(String token){
        String email = tokenProvider.getEmailBytoken(token);
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new NoSuchElementException("없는 유저입니다.")
        );
    }
}
