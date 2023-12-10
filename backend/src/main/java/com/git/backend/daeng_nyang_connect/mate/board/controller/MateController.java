package com.git.backend.daeng_nyang_connect.mate.board.controller;

import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateResponseDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.UpdateMateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.service.MateService;
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
@RequestMapping("/api/mate")
public class MateController {

    private final MateService mateService;

    @GetMapping("/all")
    public Page<MateResponseDTO> findAllMates(Pageable pageable) {
        return mateService.findAllMates(pageable);
    }

    @GetMapping("/my_board")
    public List<MateResponseDTO> findUserMates(@RequestHeader("access_token") String token) {
        return mateService.findUserMates(token);
    }

    @GetMapping("/search")
    public List<MateDTO> searchBoard(@RequestParam String keyword){
        return mateService.searchBoard(keyword);
    }

    @PostMapping("/upload")
    public Map<?,?> uploadMate(@RequestHeader("access_token") String token,
                            @RequestPart("data") MateDTO mateDTO,
                            @RequestPart("files") List<MultipartFile> fileList){
        return mateService.uploadMate(mateDTO, token, fileList);
    }

    @PutMapping("/update")
    public Map<?,?> updateMate(@RequestHeader("access_token") String token,
                                @RequestPart("data") UpdateMateDTO updateMateDTO,
                                @RequestPart(value = "files", required = false) List<MultipartFile> fileList) {
        return mateService.updateMate(updateMateDTO, token, fileList);
    }

    @DeleteMapping("/delete")
    public Map<?, ?> deleteMate(@RequestHeader("access_token") String token,
                                @RequestBody Map<String, Long> request){
        Long mateBoardId = request.get("mateBoardId");
        return mateService.deleteMate(mateBoardId, token);
    }

    @Transactional
    @PostMapping("/like")
    public ResponseEntity<Map<String, String>> addLike(@RequestHeader("access_token") String token,
                                                       @RequestParam("mateBoardId") Long mateBoardId) {
        return mateService.clickLike(mateBoardId, token);
    }

}
