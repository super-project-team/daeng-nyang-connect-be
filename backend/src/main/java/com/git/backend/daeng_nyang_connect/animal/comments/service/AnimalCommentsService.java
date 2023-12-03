package com.git.backend.daeng_nyang_connect.animal.comments.service;

import com.git.backend.daeng_nyang_connect.animal.board.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.dto.request.UpdateAnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalLike;
import com.git.backend.daeng_nyang_connect.animal.board.repository.AnimalLikeRepository;
import com.git.backend.daeng_nyang_connect.animal.board.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.comments.entity.AnimalComments;
import com.git.backend.daeng_nyang_connect.animal.comments.entity.AnimalCommentsLike;
import com.git.backend.daeng_nyang_connect.animal.comments.repository.AnimalCommentsLikeRepository;
import com.git.backend.daeng_nyang_connect.animal.comments.repository.AnimalCommentsRepository;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnimalCommentsService {
    private final AnimalRepository animalRepository;
    private final AnimalCommentsRepository animalCommentsRepository;
    private final AnimalCommentsLikeRepository animalCommentsLikeRepository;
    private final UserRepository userRepository;

    public AnimalComments addComments(Long animalId, String comments, String token) {
//        1. 토큰으로 유저 확인

//        2. 댓글 DB에 저장
        Animal animal = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );
        Instant instant = Instant.now();
        Timestamp createdAt = Timestamp.from(instant);

        AnimalComments addComments = AnimalComments.builder()
                                                .animal(animal)
                                                .user(user)
                                                .nickname(user.getNickname())
                                                .contents(comments)
                                                .like(0)
                                                .createdAt(createdAt)
                                                .build();
        animalCommentsRepository.save(addComments);

//        3. return 새로운 댓글
        return addComments;
    }

    public void deleteComments(Long commentsId, String token) {
//        1. 토큰으로 유저 확인
        AnimalComments myComment = animalCommentsRepository.findById(commentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        if(!myComment.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 작성 댓글입니다.");
        }


//        2. 작성자와 유저가 같다면 댕냥이 DB에서 삭제
        animalCommentsRepository.deleteById(commentsId);
    }

    public AnimalComments updateComments(Long commentsId, String comments, String token) {
//        1. 토큰으로 유저 확인
        AnimalComments myComment = animalCommentsRepository.findById(commentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        if(!myComment.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 작성 댓글입니다.");
        }

//        2. 댕냥이 정보 DB에서 수정
        AnimalComments myComments = animalCommentsRepository.findById(commentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        AnimalComments updateComments = AnimalComments.builder()
                                                .commentsId(commentsId)
                                                .animal(myComments.getAnimal())
                                                .user(myComments.getUser())
                                                .nickname(myComments.getNickname())
                                                .contents(comments)
                                                .like(myComments.getLike())
                                                .createdAt(myComments.getCreatedAt())
                                                .build();
        animalCommentsRepository.save(updateComments);

//        3. return 수정된 댕냥이
        return updateComments;
    }

    public List<AnimalComments> findAllCommentsToAnimal(Long animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );
//        return DB 댕냥 리스트 반환
        return animalCommentsRepository.findByAnimal(animal);
    }


    public Map<String, String> likeComments(Long commentsId, String token) {
//        1. 토큰으로 유저 확인

//        2. 해당 유저가 해당 댕냥이에게 좋아요를 눌렀는지 확인
        AnimalComments myComment = animalCommentsRepository.findById(commentsId).orElseThrow(
                () -> new NoSuchElementException("없는 댓글입니다.")
        );

        Map<String,String> message = new HashMap<>();

        if(animalCommentsLikeRepository.findByUser(user).isEmpty()){
            AnimalCommentsLike addLike = AnimalCommentsLike.builder()
                                                        .animalComments(myComment)
                                                        .user(user)
                                                        .build();
            animalCommentsLikeRepository.save(addLike);
            message.put("message", "좋아요가 성공적으로 추가되었습니다.");
            return message;
        }

        animalCommentsLikeRepository.deleteByUser(user);
        message.put("message", "좋아요가 성공적으로 삭제되었습니다.");
        return message;
    }
}
