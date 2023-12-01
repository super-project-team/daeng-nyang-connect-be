package com.git.backend.daeng_nyang_connect.animal.board.service;

import com.git.backend.daeng_nyang_connect.animal.board.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.dto.request.UpdateAnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    public Animal addAnimal(AnimalRequestDTO animalRequestDTO, String token) {
//        1. 토큰으로 유저 확인

//        2. 댕냥이 DB에 저장

//        3. 댕냥이 이미지 DB에 저장

//        4. return 새로운 댕냥이

    }

    public void deleteAnimal(Long animalId, String token) {
//        1. 토큰으로 유저 확인

//        2. 댕냥이 DB에서 삭제


    }

    public Animal updateAnimal(UpdateAnimalRequestDTO updateAnimalRequestDTO, String token) {
//        1. 토큰으로 유저 확인

//        2. 댕냥이 정보 DB에서 수정


//        3. return 수정된 댕냥이
    }

    public List<Animal> findAllAnimal() {
//        return DB 댕냥 리스트 반환

    }


    public String likeAnimal(String token) {
//        1. 토큰으로 유저 확인

//        2. 해당 유저가 해당 댕냥이에게 좋아요를 눌렀는지 확인

//        3. return message
    }
}
