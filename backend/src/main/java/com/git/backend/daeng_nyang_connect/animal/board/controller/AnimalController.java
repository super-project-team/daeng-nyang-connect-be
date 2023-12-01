package com.git.backend.daeng_nyang_connect.animal.board.controller;

import com.git.backend.daeng_nyang_connect.animal.board.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.dto.request.UpdateAnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.board.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
public class AnimalController {
//    CRUD : 파양동물 등록 * 삭제 * 정보 수정 * 조회
    private final AnimalService animalService;
    @PostMapping("/add")
    public ResponseEntity<?> addAnimal(@RequestBody AnimalRequestDTO animalRequestDTO,
                                              @RequestHeader("X-AUTH-TOKEN") String token){
        Animal newAnimal = animalService.addAnimal(animalRequestDTO, token);
        return ResponseEntity.status(200).body(newAnimal);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAnimal(@RequestParam("animalId") Long animalId,
                                                 @RequestHeader("X-AUTH-TOKEN") String token){
        animalService.deleteAnimal(animalId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateAnimal(@RequestBody UpdateAnimalRequestDTO updateAnimalRequestDTO,
                                                 @RequestHeader("X-AUTH-TOKEN") String token){
        Animal updateAnimal = animalService.updateAnimal(updateAnimalRequestDTO, token);
        return ResponseEntity.status(200).body(updateAnimal);
    }

    @GetMapping("/all")
    public List<Animal> findAllAnimal(@RequestHeader("X-AUTH-TOKEN") String token){
        return animalService.findAllAnimal();
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeAnimal(@RequestHeader("X-AUTH-TOKEN") String token){
        String message = animalService.likeAnimal(token);
        return ResponseEntity.status(200).body(message);
    }
}
