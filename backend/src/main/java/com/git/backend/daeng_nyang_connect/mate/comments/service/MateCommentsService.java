package com.git.backend.daeng_nyang_connect.mate.comments.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateRepository;
import com.git.backend.daeng_nyang_connect.mate.comments.dto.MateCommentsDTO;
import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateComments;
import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateCommentsLike;
import com.git.backend.daeng_nyang_connect.mate.comments.repository.MateCommentsLikeRepository;
import com.git.backend.daeng_nyang_connect.mate.comments.repository.MateCommentsRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Cacheable
public class MateCommentsService {

    private final TokenProvider tokenProvider;
    private final MateRepository mateRepository;
    private final MateCommentsRepository mateCommentsRepository;
    private final MateCommentsLikeRepository mateCommentsLikeRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> uploadComment(String token, Long mate, MateCommentsDTO mateCommentsDTO){

        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();
        Mate byId = mateRepository.findById(mate).orElseThrow();

        MateComments mateComments = MateComments.builder()
                .mateCommentsId(mateCommentsDTO.getMateCommentsId())
                .comment(mateCommentsDTO.getComment())
                .mate(byId)
                .createdAt(mateCommentsDTO.getCreatedAt())
                .user(user)
                .mateCommentsLike(0)
                .build();

        mateCommentsRepository.save(mateComments);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글이 등록 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateComment(String token, Long mateCommentsId, MateCommentsDTO mateCommentsDTO){

        MateComments mateComments = checkMyComment(mateCommentsId, token);

        mateComments.setComment(mateCommentsDTO.getComment());
        mateComments.setCreatedAt(mateComments.getCreatedAt());

        mateCommentsRepository.save(mateComments);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글 수정 완료 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteComment(String token, Long mateCommentsId){

        MateComments mateComments = checkMyComment(mateCommentsId, token);
        mateCommentsRepository.delete(mateComments);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글 삭제 완료 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);

    }

    //내가 쓴 댓글인지 확인
    public MateComments checkMyComment(Long mateCommentsId, String token){
        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();

        MateComments mateComments = mateCommentsRepository.findById(mateCommentsId)
                .orElseThrow();
        List<MateComments> byUser = mateCommentsRepository.findByUser(user);

        if(byUser.contains(mateComments)){
            return mateComments;
        }else{
            return null;
        }
    }
    //좋아요 로직
    public void setHeart(MateComments mateComments, User user, Integer likeCount, Boolean msg){
        if(msg){
            MateCommentsLike mateCommentsLike = new MateCommentsLike(user,mateComments);
            likeCount++;
            mateComments.setMateCommentsLike(likeCount);
            mateCommentsLikeRepository.save(mateCommentsLike);
            mateCommentsRepository.save(mateComments);
        }else{
            mateCommentsLikeRepository.deleteByUser(user);
            likeCount--;
            mateComments.setMateCommentsLike(likeCount);
            mateCommentsRepository.save(mateComments);
        }
    }
    //좋아요 클릭
    @Transactional
    public ResponseEntity<String> clickLike(Long mateCommentId, String token){

        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();

        MateComments isMateComment = mateCommentsRepository.findById(mateCommentId)
                .orElseThrow();

        if(mateCommentsLikeRepository.findByUser(user).isEmpty()){
            setHeart(isMateComment, user, isMateComment.getMateCommentsLike(), true);
            return ResponseEntity.ok().body(mateCommentId + "번 게시글에 좋아요가 추가 되었습니다");
        }
        else{
            setHeart(isMateComment, user, isMateComment.getMateCommentsLike(), false);
            return ResponseEntity.ok().body(mateCommentId + "번 게시글에 좋아요가 취소 되었습니다");
        }
    }
}
