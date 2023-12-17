package com.git.backend.daeng_nyang_connect.mypet.board.controller;

import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetResponseDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.service.MyPetService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/getAll")
    public List<MyPetResponseDTO> findAllMyPet(Pageable pageable) {
        return myPetService.findAllMyPet(pageable);
    }

    @GetMapping("/getBoard")
    public MyPetResponseDTO getThisBoard(@RequestParam("id") Long myPet) {
        return myPetService.getThisBoard(myPet);
    }

    @GetMapping("/search")
    public List<MyPetDTO> searchBoard(@RequestParam String keyword, Pageable pageable){
        return myPetService.searchBoard(keyword, pageable);
    }

    @PostMapping("/post")
    public Map<?,?> postMyPet(@RequestHeader("access_token") String token,
                              MyPetDTO myPetDTO,
                              List<MultipartFile> files){
        return myPetService.postMyPet(myPetDTO, token, files);
    }

    @PutMapping("/modify")
    public Map<?,?> modifyMyPet(@RequestHeader("access_token") String token,
                                @RequestParam("myPetId") Long myPetId,
                                MyPetDTO myPetDTO,
                                MultipartFile files) throws FileUploadFailedException {
        return myPetService.modifyMyPet(myPetId, myPetDTO, token, files);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteMyPet(@RequestHeader("access_token") String token,
                                 @RequestParam("myPetId") Long myPetId){
        return myPetService.deleteMyPet(myPetId, token);
    }

    @Transactional
    @PostMapping("/like")
    public Map<String, String> addLike(@RequestHeader("access_token") String token,
                                       @RequestParam("myPetId") Long myPetBoardId) {
        return myPetService.clickLike(myPetBoardId, token);
    }

}
