package com.git.backend.daeng_nyang_connect.animal.service;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.*;
import com.git.backend.daeng_nyang_connect.animal.repository.AdoptedAnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalImageRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalScrapRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl  implements AnimalService{
    private final AnimalRepository animalRepository;
    private final AnimalImageRepository animalImageRepository;
    private final AnimalScrapRepository animalScrapRepository;
    private final AdoptedAnimalRepository adoptedAnimalRepository;
    private final UserRepository userRepository;



    @Override
    public Animal addAnimal(AnimalRequestDTO animalRequestDTO, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 댕냥이 DB에 저장
        Animal newAnimal = Animal.builder()
                                .user(user)
                                .animalName(animalRequestDTO.getAnimalName())
                                .age(animalRequestDTO.getAge())
                                .gender(animalRequestDTO.getGender())
                                .disease(animalRequestDTO.getDisease())
                                .training(animalRequestDTO.getTraining())
                                .neutering(animalRequestDTO.getNeutering())
                                .textReason(animalRequestDTO.getTextReason())
                                .healthCheck(animalRequestDTO.getHealthCheck())
                                .breed(animalRequestDTO.getBreed())
                                .kind(animalRequestDTO.getKind())
                                .nurturePeriod(animalRequestDTO.getNurturePeriod())
                                .adoptionStatus(AdoptionStatus.PROGRESS)
                                .createdAt(nowDate())
                                .build();
        animalRepository.save(newAnimal);

        // 3. 이미지 url 변환
        if(!animalRequestDTO.getImages().isEmpty()) {

        }

        // 4. 댕냥이 이미지 DB에 저장
        AnimalImage animalImage = AnimalImage.builder()
                                            .animal(newAnimal)
                                            .url(url)
                                            .build();
        animalImageRepository.save(animalImage);

        // 4. return 새로운 댕냥이
        return newAnimal;
    }

    @Override
    public AdoptedAnimal completeAnimal(Long animalId, Long adoptedUserId, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 내가 작성한 글이 맞는지 확인
        Animal myAnimal = checkMyBoard(animalId, user);

        // 3. 입양 진행 상태(adoption_status)를 COMPLETED로 변경
        myAnimal.updateAdoptionStatus(AdoptionStatus.COMPLETED);
        animalRepository.save(myAnimal);

        // 4. 해당 댕냥이와 입양한 유저를 함께 입양된 동물(AdoptedAnimal) DB에 추가
        AdoptedAnimal adoptedAnimal = AdoptedAnimal.builder()
                                                    .animal(myAnimal)
                                                    .user(adoptedUser)
                                                    .build();
        adoptedAnimalRepository.save(adoptedAnimal);

        return adoptedAnimal;
    }

    @Override
    public void deleteAnimal(Long animalId, String token) {
        // 1. 토큰으로 유저 확인

        // 2. 내가 작성한 글이 맞는지 확인
        Animal myAnimal = checkMyBoard(animalId, user);

        // 3. 해당 댕냥이 게시글을 DB에서 삭제
        animalRepository.delete(myAnimal);
    }


    @Override
    public Animal updateAnimal(Long animalId, AnimalRequestDTO animalRequestDTO, String token) {
        // 1. 토큰으로 유저 확인


        // 2. 내가 작성한 글이 맞는지 확인
        Animal myAnimal = checkMyBoard(animalId, user);

        // 3. 댕냥이 게시글 정보를 DB에서 수정
        animalRequestDTO.checkUpdateList(animalRequestDTO, myAnimal);
        Animal updateAnimal = Animal.builder()
                                    .animalId(myAnimal.getAnimalId())
                                    .user(myAnimal.getUser())
                                    .animalName(animalRequestDTO.getAnimalName())
                                    .age(animalRequestDTO.getAge())
                                    .gender(animalRequestDTO.getGender())
                                    .disease(animalRequestDTO.getDisease())
                                    .training(animalRequestDTO.getTraining())
                                    .neutering(animalRequestDTO.getNeutering())
                                    .textReason(animalRequestDTO.getTextReason())
                                    .healthCheck(animalRequestDTO.getHealthCheck())
                                    .breed(animalRequestDTO.getBreed())
                                    .kind(animalRequestDTO.getKind())
                                    .nurturePeriod(animalRequestDTO.getNurturePeriod())
                                    .adoptionStatus(myAnimal.getAdoptionStatus())
                                    .createdAt(myAnimal.getCreatedAt())
                                    .build();
        animalRepository.save(updateAnimal);

        // 4. 수정된 댕냥이 게시글을 반환
        return updateAnimal;
    }


    @Override
    public List<Animal> findAllAnimal() {
        // DB에 저장된 댕냥이 리스트 반환, 없다면 null 반환
        return animalRepository.findAll();
    }

    @Override
    public Animal scrapAnimal(Long animalId, String token) {
        // 1. 토큰으로 유저 확인


        // 2. 게시글 존재 유무 확인
        Animal animalBoard = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        // 3. 댕냥이와 유저 정보를 스크랩 댕냥이 DB에 함께 저장
        AnimalScrap myScrap = AnimalScrap.builder()
                                        .animal(animalBoard)
                                        .user(user)
                                        .build();
        animalScrapRepository.save(myScrap);

        // 4. 내가 스크랩 한 댕냥이 게시글을 반환
        return animalBoard;
    }

    @Override
    public Animal checkMyBoard(Long animalId, User user){
        // 1. 게시글 존재 유무 확인
        Animal myAnimal = animalRepository.findById(animalId).orElseThrow(
                () -> new NoSuchElementException("없는 게시글입니다.")
        );

        // 2. 내가 작성한 게시글이 맞는지 확인
        if(!myAnimal.getUser().equals(user)){
            throw new IllegalArgumentException("다른 유저의 작성 댓글입니다.");
        }
        // 3. 내가 작성한 게시글 반환
        return myAnimal;
    }

    @Override
    public Timestamp nowDate(){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.valueOf(currentDateTime);
    }
}
