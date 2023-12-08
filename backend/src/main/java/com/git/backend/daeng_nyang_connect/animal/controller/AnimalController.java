package com.git.backend.daeng_nyang_connect.animal.controller;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.dto.response.AnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
@EnableCaching
public class AnimalController {
//    CRUD : 파양동물 등록 * 삭제 * 정보 수정 * 조회
    private final AnimalService animalService;
    @PostMapping("/add")
    public ResponseEntity<?> addAnimal(@RequestPart("dto")AnimalRequestDTO animalRequestDTO,
                                       @RequestPart("files") List<MultipartFile> files,
                                       @RequestHeader("access_token") String token){
        Animal newAnimal = animalService.addAnimal(animalRequestDTO, files, token);
        AnimalResponseDTO animalResponseDTO = animalService.response(newAnimal);
        return ResponseEntity.status(200).body(animalResponseDTO);
    }

    @Transactional
    @PutMapping("/complete")
    public ResponseEntity<?> completeAnimal(@RequestParam("animalId") Long animalId,
                                            @RequestParam("adoptedUserId") Long adoptedUserId,
                                            @RequestHeader("access_token") String token){
        AdoptedAnimal adoptedAnimal = animalService.completeAnimal(animalId, adoptedUserId, token);
        return ResponseEntity.status(200).body(adoptedAnimal.getAnimal().getAnimalName() + " 이 " + adoptedAnimal.getUser().getNickname() +" 님에게 입양되었습니다.");
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAnimal(@RequestParam("animalId") Long animalId,
                                          @RequestHeader("access_token") String token){
        animalService.deleteAnimal(animalId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateAnimal(@RequestParam("animalId") Long animalId,
                                          @RequestPart("dto") AnimalRequestDTO animalRequestDTO,
                                          @RequestPart("files") List<MultipartFile> files,
                                          @RequestHeader("access_token") String token){
        Animal updateAnimal = animalService.updateAnimal(animalId, animalRequestDTO, files, token);
        return ResponseEntity.status(200).body(updateAnimal);
    }

    @GetMapping("/all")
    public List<Animal> findAllAnimal(@RequestHeader("access_token") String token){
        return animalService.findAllAnimal();
    }

    @PostMapping("/scrap")
    public ResponseEntity<?> scrapAnimal(@RequestParam("animalId") Long animalId,
                                        @RequestHeader("access_token") String token){
        Map<String, String> message = animalService.scrapAnimal(animalId, token);
        return ResponseEntity.status(200).body(message);
    }
}
