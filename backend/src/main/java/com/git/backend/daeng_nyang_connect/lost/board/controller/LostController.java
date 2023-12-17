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
    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<?,?> addLost(@RequestHeader("access_token") String token,
                             LostBoardDetailDTO lostBoardDetailDTO,
                             List<MultipartFile> files){
        return lostService.addLost(lostBoardDetailDTO, token, files);
    }

    //lost 삭제
    @DeleteMapping("/delete")
    public Map<String,String> deleteLost(@RequestHeader("access_token")String token,
                                         @RequestParam("id")Long lostBoardId){
        return lostService.deleteLost(token,lostBoardId);
    }

    //lost 수정
    @PutMapping("/modify")
    public Map<String, String> modify(@RequestHeader("access_token") String token,
                                      @RequestParam("id")Long lostBoardId,
                                      @RequestParam("lostImgId")Long lostImgId,
                                      LostBoardDetailDTO lostBoardDetailDTO,
                                      MultipartFile multipartFile)
        throws FileUploadFailedException {
        return lostService.modifyLost(token, lostBoardId, lostBoardDetailDTO, lostImgId, multipartFile);
    }

    //모든 lost
    @GetMapping("/getAll")
    public List<LostBoardDTO> getAll(Pageable pageable){
        return lostService.getAll(pageable);
    }

    //하나의 lost
    @GetMapping("/getBoard")
    public LostBoardDetailDTO getOne(@RequestParam("id")Long lostBoardId){
        return lostService.getThis(lostBoardId);
    }

    //검색한 lost
    @GetMapping("/search")
    public List<LostBoardDTO> getSearch(@RequestParam String keyword){
        return lostService.searchLost(keyword);
    }
}
