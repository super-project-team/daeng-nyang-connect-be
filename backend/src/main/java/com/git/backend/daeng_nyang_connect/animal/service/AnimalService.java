package com.git.backend.daeng_nyang_connect.animal.service;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface AnimalService {
    // 댕냥 게시판에 내 파양동물 등록
    Animal addAnimal(AnimalRequestDTO animalRequestDTO, String token);

    AdoptedAnimal completeAnimal(Long animalId, Long adoptedUserId, String token);

    // 댕냥 게시판에 내 파양동물 삭제
    void deleteAnimal(Long animalId, String token);

    // 댕냥 게시판에 등록한 내 파양동물 수정
    Animal updateAnimal(Long animalId, AnimalRequestDTO animalRequestDTO, String token);

    // 댕냥 게시판에 등록된 파양동물 전체 출력
    List<Animal> findAllAnimal();

    // 댕냥 게시판에 등록된 파양동물 스크랩
    Animal scrapAnimal(Long animalId, String token);

    // 내가 작성한 게시글인지 확인
    Animal checkMyBoard(Long animalId, User user);

    // 현재 시간을 Timestamp로 반환
    public Timestamp nowDate();

    // url로 변환된 이미지 DB에 저장
    void uploadImage(Animal animal, AnimalImage url);

    // 토큰으로 user의 email 반환
    User checkUserByToken(String token);
}
