package com.git.backend.daeng_nyang_connect.review.comments.service;

import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewRepository;
import com.git.backend.daeng_nyang_connect.review.comments.entity.ReviewComments;
import com.git.backend.daeng_nyang_connect.review.comments.entity.ReviewCommentsLike;
import com.git.backend.daeng_nyang_connect.review.comments.repository.ReviewCommentsLikeRepository;
import com.git.backend.daeng_nyang_connect.review.comments.repository.ReviewCommentsRepository;
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
public class ReviewCommentsServiceImpl implements ReviewCommentsService {
    private final ReviewRepository reviewRepository;
    private final ReviewCommentsLikeRepository reviewCommentsLikeRepository;
    private final ReviewCommentsRepository reviewCommentsRepository;

    @Override
    public ReviewComments addCommentsOnReview(Long reviewId, String comment, String token) {
        // 1. 토큰으로 유저 확인


        // 2. 댓글 존재 유무 확인
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("없는 후기입니다.")
        );


        // 3. 댓글 DB에 저장
        ReviewComments newComment = ReviewComments.builder()
                                                .user(user)
                                                .review(review)
                                                .comment(comment)
                                                .like(0)
                                                .createdAt(nowDate())
                                                .build();
        reviewCommentsRepository.save(newComment);

        // 4. 새로운 댓글 반환
        return newComment;
    }

    @Override
    public void deleteCommentsOnReview(Long reviewCommentsId, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 내가 작성한 글이 맞는지 확인
        ReviewComments myComment = checkMyComments(reviewCommentsId, user);

        // 3. 해당 댕냥이 게시글을 DB에서 삭제
        reviewCommentsRepository.delete(myComment);
    }

    @Override
    public ReviewComments updateCommentsOnReview(Long reviewCommentsId, String comment, String token) {
        // 1. 토큰으로 유저 확인


        // 2. 내가 작성한 글이 맞는지 확인
        ReviewComments myComment = checkMyComments(reviewCommentsId, user);

        // 3. 댕냥이 후기 정보를 DB에서 수정
        ReviewComments updateComment = ReviewComments.builder()
                                                    .reviewCommentsId(myComment.getReviewCommentsId())
                                                    .user(myComment.getUser())
                                                    .review(myComment.getReview())
                                                    .comment(comment)
                                                    .like(myComment.getLike())
                                                    .createdAt(myComment.getCreatedAt())
                                                    .build();
        reviewCommentsRepository.save(updateComment);
        // 4. 수정된 댕냥이 후기를 반환
        return updateComment;
    }

    @Override
    public List<ReviewComments> findAllCommentsByReview(Long reviewId) {
        // 해당 댕냥이에 대한 후기 찾기
        return reviewCommentsRepository.findAllCommentsByReviewId(reviewId);
    }

    @Override
    public Map<String, String> likeCommentsOnReview(Long reviewCommentsId, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 해당 유저가 해당 댕냥이 후기에 좋아요를 눌렀는지 확인
        ReviewComments reviewComments = reviewCommentsRepository.findById(reviewCommentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        Map<String,String> message = new HashMap<>();

        // 3-1. 만약 해당 댓글에 좋아요가 이미 눌러져 있다면 (좋아요 - 1)
        if(reviewCommentsLikeRepository.findByUser(user).isPresent()){
            // 4. 좋아요 목록에서 삭제
            reviewCommentsLikeRepository.deleteByUser(user);

            // 5. 총 좋아요 수 수정 (좋아요 - 1)
            updateLike(reviewComments, reviewCommentsLikeRepository.totalReviewLike(reviewCommentsId))

            message.put("delete", "좋아요가 성공적으로 삭제되었습니다.");
            return message;
        }

        // 3-2. 해당 댓글에 좋아요를 처음 누를때 (좋아요 + 1) - 좋아요 목록에 추가
        ReviewCommentsLike addLike = ReviewCommentsLike.builder()
                                                    .reviewComments(reviewComments)
                                                    .user(user)
                                                    .build();
        reviewCommentsLikeRepository.save(addLike);

        // 4. 총 좋아요 수 수정 (좋아요 + 1)
        updateLike(reviewComments, reviewCommentsLikeRepository.totalReviewLike(reviewCommentsId))

        message.put("add", "좋아요가 성공적으로 추가되었습니다.");
        return message;
    }

    @Override
    public ReviewComments checkMyComments(Long reviewCommentsId, User user) {
        // 1. 댓글 존재 유무 확인
        ReviewComments myComment = reviewCommentsRepository.findById(reviewCommentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        // 2. 내가 작성한 댓글이 맞는지 확인
        if(!myComment.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 댓글 댓글입니다.");
        }
        // 3. 내가 작성한 게시글 반환
        return myComment;
    }

    @Override
    public Timestamp nowDate(){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.valueOf(currentDateTime);
    }

    @Override
    public void updateLike(ReviewComments reviewComments, Integer like){
        ReviewComments totalLike = ReviewComments.builder()
                                                .reviewCommentsId(reviewComments.getReviewCommentsId())
                                                .user(reviewComments.getUser())
                                                .review(reviewComments.getReview())
                                                .comment(reviewComments.getComment())
                                                .createdAt(reviewComments.getCreatedAt())
                                                .like(like) // 좋아요만 수정
                                                .build();
        reviewCommentsRepository.save(totalLike);
    }
}
