package com.git.backend.daeng_nyang_connect.mypet.comments.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import com.git.backend.daeng_nyang_connect.mypet.board.repository.MyPetRepository;
import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsDTO;
import com.git.backend.daeng_nyang_connect.mypet.comments.entity.MyPetComments;
import com.git.backend.daeng_nyang_connect.mypet.comments.entity.MyPetCommentsLike;
import com.git.backend.daeng_nyang_connect.mypet.comments.repository.MyPetCommentsLikeRepository;
import com.git.backend.daeng_nyang_connect.mypet.comments.repository.MyPetCommentsRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Cacheable
public class MyPetCommentsService {

    private final TokenProvider tokenProvider;
    private final MyPetRepository myPetRepository;
    private final MyPetCommentsRepository myPetCommentsRepository;
    private final MyPetCommentsLikeRepository myPetCommentsLikeRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> uploadComment(String token, Long myPet, MyPetCommentsDTO myPetCommentsDTO){

        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();
        MyPet byId = myPetRepository.findById(myPet).orElseThrow();

        MyPetComments myPetComments = MyPetComments.builder()
                .myPetCommentsId(myPetCommentsDTO.getMyPetCommentsId())
                .comment(myPetCommentsDTO.getComment())
                .myPet(byId)
                .createdAt(myPetCommentsDTO.getCreatedAt())
                .user(user)
                .myPetCommentsLike(0)
                .build();

        myPetCommentsRepository.save(myPetComments);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글이 등록 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateComment(String token, Long myPetCommentsId, MyPetCommentsDTO myPetCommentsDTO){

        MyPetComments myPetComments = checkMyComment(myPetCommentsId, token);

        myPetComments.setComment(myPetCommentsDTO.getComment());
        myPetComments.setCreatedAt(myPetComments.getCreatedAt());

        myPetCommentsRepository.save(myPetComments);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글 수정 완료 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> deleteComment(String token, Long myPetCommentsId){

        MyPetComments myPetComments = checkMyComment(myPetCommentsId, token);
        myPetCommentsRepository.delete(myPetComments);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "댓글 삭제 완료 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return ResponseEntity.ok(response);

    }

    //내가 쓴 댓글인지 확인
    public MyPetComments checkMyComment(Long myPetCommentsId, String token){
        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();

        MyPetComments myPetComments = myPetCommentsRepository.findById(myPetCommentsId)
                .orElseThrow();
        List<MyPetComments> byUser = myPetCommentsRepository.findByUser(user);

        if(byUser.contains(myPetComments)){
            return myPetComments;
        }else{
            return null;
        }
    }
    //좋아요 로직
    public void setHeart(MyPetComments myPetComments, User user, Integer likeCount, Boolean msg){
        if(msg){
            MyPetCommentsLike myPetCommentsLike = new MyPetCommentsLike(user,myPetComments);
            likeCount++;
            myPetComments.setMyPetCommentsLike(likeCount);
            myPetCommentsLikeRepository.save(myPetCommentsLike);
            myPetCommentsRepository.save(myPetComments);
        }else{
            myPetCommentsLikeRepository.deleteByUser(user);
            likeCount--;
            myPetComments.setMyPetCommentsLike(likeCount);
            myPetCommentsRepository.save(myPetComments);
        }
    }
    //좋아요 클릭
    @Transactional
    public ResponseEntity<String> clickLike(Long myPetCommentsId, String token){

        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow();

        MyPetComments isMyPetComment = myPetCommentsRepository.findById(myPetCommentsId)
                .orElseThrow();

        if(myPetCommentsLikeRepository.findByUser(user).isEmpty()){
            setHeart(isMyPetComment, user, isMyPetComment.getMyPetCommentsLike(), true);
            return ResponseEntity.ok().body(myPetCommentsId + "번 게시글에 좋아요가 추가 되었습니다");
        }
        else{
            setHeart(isMyPetComment, user, isMyPetComment.getMyPetCommentsLike(), false);
            return ResponseEntity.ok().body(myPetCommentsId + "번 게시글에 좋아요가 취소 되었습니다");
        }
    }

}
