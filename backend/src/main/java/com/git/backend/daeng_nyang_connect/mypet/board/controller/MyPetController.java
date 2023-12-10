package com.git.backend.daeng_nyang_connect.mypet.board.controller;

import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetResponseDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.UpdateMyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.service.MyPetService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/my_pet")
public class MyPetController {

    private final MyPetService myPetService;

    @GetMapping("/all")
    public Page<MyPetResponseDTO> findAllMyPet(Pageable pageable) {
        return myPetService.findAllMyPet(pageable);
    }

    @GetMapping("/my_board")
    public List<MyPetResponseDTO> findUserMyPet(@RequestHeader("access_token") String token) {
        return myPetService.findUserMyPet(token);
    }

    @GetMapping("/search")
    public List<MyPetDTO> searchBoard(@RequestParam String keyword){
        return myPetService.searchBoard(keyword);
    }

    @PostMapping("/upload")
    public Map<?,?> uploadMyPet(@RequestHeader("access_token") String token,
                               @RequestPart("data") MyPetDTO myPetDTO,
                               @RequestPart("files") List<MultipartFile> fileList){
        return myPetService.uploadMyPet(myPetDTO, token, fileList);
    }

    @PutMapping("/update")
    public Map<?,?> updateMyPet(@RequestHeader("access_token") String token,
                               @RequestPart("data") UpdateMyPetDTO updateMyPetDTO,
                               @RequestPart(value = "files", required = false) List<MultipartFile> fileList) {
        return myPetService.updateMyPet(updateMyPetDTO, token, fileList);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteMyPet(@RequestHeader("access_token") String token,
                                @RequestBody Map<String, Long> request){
        Long myPetBoardId = request.get("myPetBoardId");
        return myPetService.deleteMyPet(myPetBoardId, token);
    }

    @Transactional
    @PostMapping("/like")
    public ResponseEntity<Map<String, String>> addLike(@RequestHeader("access_token") String token,
                                                       @RequestParam("myPetBoardId") Long myPetBoardId) {
        return myPetService.clickLike(myPetBoardId, token);
    }

}
