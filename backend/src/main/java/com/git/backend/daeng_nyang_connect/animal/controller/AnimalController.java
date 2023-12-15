package com.git.backend.daeng_nyang_connect.animal.controller;

import com.git.backend.daeng_nyang_connect.animal.dto.request.AnimalRequestDTO;
import com.git.backend.daeng_nyang_connect.animal.dto.response.AnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import com.git.backend.daeng_nyang_connect.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
@EnableCaching
public class AnimalController {
//    CRUD : 파양동물 등록 * 삭제 * 정보 수정 * 조회
    private final AnimalService animalService;
    @PostMapping("/post")
    public ResponseEntity<?> addAnimal(AnimalRequestDTO animalRequestDTO,
                                       List<MultipartFile> files,
                                       @RequestHeader("access_token") String token){
        Animal newAnimal = animalService.addAnimal(animalRequestDTO, files, token);
        AnimalResponseDTO response = animalService.response(newAnimal);
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @PutMapping("/complete")
    public ResponseEntity<?> completeAnimal(@RequestParam("animalId") Long animalId,
                                            @RequestParam("adoptedUserId") Long adoptedUserId,
                                            @RequestHeader("access_token") String token){
        AdoptedAnimal adoptedAnimal = animalService.completeAnimal(animalId, adoptedUserId, token);
        AnimalResponseDTO response = animalService.response(adoptedAnimal);
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAnimal(@RequestParam("animalId") Long animalId,
                                          @RequestHeader("access_token") String token){
        animalService.deleteAnimal(animalId, token);
        return ResponseEntity.status(200).body("게시글이 삭제되었습니다.");
    }

    @Transactional
    @PutMapping("/modify")
    public ResponseEntity<?> updateAnimal(@RequestParam("animalId") Long animalId,
                                          AnimalRequestDTO animalRequestDTO,
                                          List<MultipartFile> files,
                                          @RequestHeader("access_token") String token){
        Animal updateAnimal = animalService.updateAnimal(animalId, animalRequestDTO, files, token);
        AnimalResponseDTO response = animalService.response(updateAnimal);
        return ResponseEntity.status(200).body(response);
    }

    // 조회 - 전체 * kind(동물 종류) * city(지역별) * 입양 완료 상태별
    @GetMapping("/getAll")
    public ResponseEntity<?> findAllAnimal(){
        List<Animal> animalList = animalService.findAllAnimal();
        List<AnimalResponseDTO> responseList = animalService.responseList(animalList);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/kind/{kind}")
    public ResponseEntity<?> findAnimalByKind(@PathVariable("kind") Kind kind) {
        List<Animal> animalList = animalService.findAnimalByKind(kind);
        List<AnimalResponseDTO> responseList = animalService.responseList(animalList);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<?> findAnimalByCity(@PathVariable("city") String city) {
        List<Animal> animalList = animalService.findAnimalByCity(city);
        List<AnimalResponseDTO> responseList = animalService.responseList(animalList);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/adoptionStatus/{adoptionStatus}")
    public ResponseEntity<?> findAnimalByAdoptionStatus(@PathVariable("adoptionStatus") AdoptionStatus adoptionStatus) {
        List<Animal> animalList = animalService.findAnimalByAdoptionStatus(adoptionStatus);
        List<AnimalResponseDTO> responseList = animalService.responseList(animalList);
        return ResponseEntity.status(200).body(responseList);
    }

    @Transactional
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapAnimal(@RequestParam("animalId") Long animalId,
                                        @RequestHeader("access_token") String token){
        Map<String, String> message = animalService.scrapAnimal(animalId, token);
        return ResponseEntity.status(200).body(message);
    }
}