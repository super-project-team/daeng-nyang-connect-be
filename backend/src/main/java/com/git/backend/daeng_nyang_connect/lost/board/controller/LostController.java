package com.git.backend.daeng_nyang_connect.lost.board.controller;

import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.lost.board.dto.LostBoardDTO;
import com.git.backend.daeng_nyang_connect.lost.board.dto.LostBoardDetailDTO;
import com.git.backend.daeng_nyang_connect.lost.board.service.LostService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lost")
@Slf4j
@Cacheable
public class LostController {
    private final LostService lostService;

    //lost 등록
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<?,?> addLost(@RequestHeader("access_token") String token,
                            @RequestPart("data") LostBoardDetailDTO lostBoardDetailDTO,
                            @RequestPart("files") List<MultipartFile> fileList){
        return lostService.addLost(lostBoardDetailDTO, token, fileList);
    }

    //lost 삭제
    @DeleteMapping("/delete")
    public Map<String,String> deleteLost(@RequestHeader("access_token")String token,
                                         @RequestParam("lostId")Long lostBoardId){
        return lostService.deleteLost(token,lostBoardId);
    }

    //lost 수정
    @PutMapping("/modify")
    public Map<String, String> modify(@RequestHeader("access_token") String token,
                                      @RequestParam("lostId")Long lostBoardId,
                                      @RequestParam("lostImgId")Long lostImgId,
                                      @RequestPart("data") LostBoardDetailDTO lostBoardDetailDTO,
                                      @RequestPart("img")MultipartFile multipartFile)
        throws FileUploadFailedException {
        return lostService.modifyLost(token, lostBoardId, lostBoardDetailDTO, lostImgId, multipartFile);
    }

    //모든 lost
    @GetMapping("/all")
    public List<LostBoardDTO> getAll(Pageable pageable){
        return lostService.getAll(pageable);
    }

    //하나의 lost
    @GetMapping("/one")
    public LostBoardDetailDTO getOne(@RequestParam("lostId")Long lostBoardId){
        return lostService.getThis(lostBoardId);
    }

    //검색한 lost
    @GetMapping("/search")
    public List<LostBoardDTO> getSearch(@RequestParam String keyword){
        return lostService.searchLost(keyword);
    }
}
