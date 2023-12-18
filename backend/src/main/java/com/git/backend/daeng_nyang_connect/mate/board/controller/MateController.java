package com.git.backend.daeng_nyang_connect.mate.board.controller;

import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateResponseDTO;
import com.git.backend.daeng_nyang_connect.mate.board.service.MateService;
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
@RequestMapping("/api/mate")
public class MateController {

    private final MateService mateService;

    @GetMapping("/getAll")
    public List<MateResponseDTO> findAllMates(Pageable pageable) {
        return mateService.findAllMates(pageable);
    }

    @GetMapping("/getBoard")
    public MateResponseDTO getThisBoard(@RequestParam("id") Long mate) {
        return mateService.getThisBoard(mate);
    }

    @GetMapping("/search")
    public List<MateDTO> searchBoard(@RequestParam String keyword, Pageable pageable) {
        return mateService.searchBoard(keyword, pageable);
    }

    @PostMapping("/post")
    public Map<?,?> postMate(@RequestHeader("access_token") String token,
                             MateDTO mateDTO,
                             List<MultipartFile> files){
        return mateService.postMate(mateDTO, token, files);
    }

    @PutMapping("/modify")
    public Map<?,?> modifyMate(@RequestHeader("access_token") String token,
                               @RequestParam("mateId") Long mateId,
                               MateDTO mateDTO,
                               MultipartFile files) throws FileUploadFailedException {
        return mateService.modifyMate(mateId, mateDTO, token, files);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteMate(@RequestHeader("access_token") String token,
                                @RequestParam("mateId") Long mateId){
        return mateService.deleteMate(mateId, token);
    }

    @Transactional
    @PostMapping("/like")
    public Map<String, String> addLike(@RequestHeader("access_token") String token,
                                       @RequestParam("mateId") Long mateId) {
        return mateService.clickLike(mateId, token);
    }

    @GetMapping("/getSize")
    public Map<String,Integer>getSize(){
        return mateService.getSize();
    }

}