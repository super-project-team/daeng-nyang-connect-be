package com.git.backend.daeng_nyang_connect.animal.controller;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.service.AnimalService;
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
    @PutMapping("/complete")
    public ResponseEntity<?> completeAnimal(@RequestParam("animalId") Long animalId,
                                            @RequestParam("adoptedUserId") Long adoptedUserId,
                                            @RequestHeader("X-AUTH-TOKEN") String token){
        AdoptedAnimal adoptedAnimal = animalService.completeAnimal(animalId, adoptedUserId, token);
        return ResponseEntity.status(200).body(adoptedAnimal.getAnimal().getAnimalName() + " 이 " + adoptedAnimal.getUser().getNickname() +" 님에게 입양되었습니다.");
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
    public ResponseEntity<?> updateAnimal(@RequestParam("animalId") Long animalId,
                                          @RequestBody AnimalRequestDTO animalRequestDTO,
                                          @RequestHeader("X-AUTH-TOKEN") String token){
        Animal updateAnimal = animalService.updateAnimal(animalId, animalRequestDTO, token);
        return ResponseEntity.status(200).body(updateAnimal);
    }

    @GetMapping("/all")
    public List<Animal> findAllAnimal(@RequestHeader("X-AUTH-TOKEN") String token){
        return animalService.findAllAnimal();
    }

    @PostMapping("/scrap")
    public ResponseEntity<?> scrapAnimal(@RequestBody Long animalId,
                                       @RequestHeader("X-AUTH-TOKEN") String token){
        Animal scrapAnimal = animalService.scrapAnimal(animalId, token);
        return ResponseEntity.status(200).body(scrapAnimal);
    }
}
