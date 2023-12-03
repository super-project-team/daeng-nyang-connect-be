package com.git.backend.daeng_nyang_connect.animal.board.service;

import com.git.backend.daeng_nyang_connect.animal.board.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.dto.request.UpdateAnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalLike;
import com.git.backend.daeng_nyang_connect.animal.board.repository.AnimalImageRepository;
import com.git.backend.daeng_nyang_connect.animal.board.repository.AnimalLikeRepository;
import com.git.backend.daeng_nyang_connect.animal.board.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalImageRepository animalImageRepository;
    private final AnimalLikeRepository animalLikeRepository;
    private final UserRepository userRepository;
    public Animal addAnimal(AnimalRequestDTO animalRequestDTO, String token) {
//        1. 토큰으로 유저 확인

//        2. 댕냥이 DB에 저장
        Animal newAnimal = AnimalRequestDTO.addToEntity(animalRequestDTO, user);
        animalRepository.save(newAnimal);

//        3. 댕냥이 이미지 DB에 저장
//        3-1. 이미지 url 변환
        if(!animalRequestDTO.getImages().isEmpty()) {
            AnimalImage animalImage = AnimalImage.builder()
                                                .animal(newAnimal)
                                                .url(url)
                                                .build();
            animalImageRepository.save(animalImage);
        }
//        4. return 새로운 댕냥이
        return newAnimal;
    }

    public void deleteAnimal(Long animalId, String token) {
//        1. 토큰으로 유저 확인
        Animal myBoard = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        if(!myBoard.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 작성 댓글입니다.");
        }

//        2. 작성자와 유저가 같다면 댕냥이 DB에서 삭제
        animalRepository.deleteById(animalId);
    }

    public Animal updateAnimal(Long animalId, UpdateAnimalRequestDTO updateAnimalRequestDTO, String token) {
//        1. 토큰으로 유저 확인
        Animal myBoard = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        if(!myBoard.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 작성 댓글입니다.");
        }

//        2. 댕냥이 정보 DB에서 수정
        Animal animal = animalRepository.findById(updateAnimalRequestDTO.getAnimalId()).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        updateAnimalRequestDTO.checkUpdateList(animal, updateAnimalRequestDTO);
        Animal updateAnimal = AnimalRequestDTO.updateToDTO(updateAnimalRequestDTO, animal);
        animalRepository.save(updateAnimal);

//        3. return 수정된 댕냥이
        return updateAnimal;
    }

    public List<Animal> findAllAnimal() {
//        return DB 댕냥 리스트 반환
        return animalRepository.findAll();
    }


    public Map<String, String> likeAnimal(Long animalId, String token) {
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
}
