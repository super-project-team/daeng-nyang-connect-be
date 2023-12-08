package com.git.backend.daeng_nyang_connect.animal.service;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.dto.response.AnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface AnimalService {
    AnimalResponseDTO response(Animal animal);

    // 댕냥 게시판에 내 파양동물 등록
    Animal addAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> files, String token);

    AdoptedAnimal completeAnimal(Long animalId, Long adoptedUserId, String token);

    // 댕냥 게시판에 내 파양동물 삭제
    void deleteAnimal(Long animalId, String token);

    // 댕냥 게시판에 등록한 내 파양동물 수정
    Animal updateAnimal(Long animalId, AnimalRequestDTO animalRequestDTO, List<MultipartFile> files, String token);

    // 댕냥 게시판에 등록된 파양동물 전체 출력
    List<Animal> findAllAnimal();

    // 댕냥 게시판에 등록된 파양동물 스크랩
    Map<String, String> scrapAnimal(Long animalId, String token);

    // 내가 작성한 게시글인지 확인
    Animal checkMyBoard(Long animalId, User user);

    // 현재 시간을 Timestamp로 반환
    public Timestamp nowDate();

    // url로 변환된 이미지 DB에 저장
    void uploadImage(Animal animal,  List<MultipartFile> multipartFileList);

    // 토큰으로 user 체크 후 user 반환
    User checkUserByToken(String token);

    // 동물의 입양 여부 확인
    AdoptionStatus checkAnimalStatus(Long animalId);
}
