package com.git.backend.daeng_nyang_connect.user.controller;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalScrap;
import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardLikeDto;
import com.git.backend.daeng_nyang_connect.user.dto.ModifyUserDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyBoardDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyPageDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyScrapAnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.user.service.FindMyBoardService;
import com.git.backend.daeng_nyang_connect.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final FindMyBoardService findMyBoardService;


    @GetMapping("/get")
    public MyPageDto getMyPage(@RequestHeader("access_token")String token){
        return myPageService.getMyInfo(token);
    }

    @PutMapping ("/modifyProfile")
    private Map<String ,String> modifyProfile(@RequestHeader("access_token")String token,
                                              @RequestBody MultipartFile multipartFile) throws FileUploadFailedException {
        return  myPageService.modifyProfile(token, multipartFile);
    }

    @PutMapping("/modifyInfo")
    private Map<String ,String> modifyInfo(@RequestHeader("access_token")String token,
                                           @RequestParam("info") String info){
        return myPageService.modifyInfo(token, info);
    }

    @PutMapping("/modifyNickname")
    public Map<String ,String > modifyNickname(@RequestHeader("access_token")String token,
                                               @RequestParam("nickname") String nickname){
        return myPageService.modifyNickname(token, nickname);
    }

    @PutMapping("/modifyCityTown")
    public ResponseEntity<?> modifyCityTown(@RequestHeader("access_token") String token,
                                            @RequestParam("city") String city,
                                            @RequestParam("town") String town){
        return myPageService.modifyCityTown(token,city,town);
    }

    @PutMapping("/modifyExperience")
    public ResponseEntity<?> modifyExperience(@RequestHeader("access_token") String token,
                                            @RequestParam("experience") Boolean experience){
        return myPageService.modifyExperience(token,experience);
    }

    @PutMapping("/modifyPassword")
    public Map<String,String > modifyPassword(@RequestHeader("access_token")String token,
                                              @RequestParam("password") String password){
        return myPageService.modifyPassword(token, password);
    }
    @PutMapping("/modifyMobile")
    public ResponseEntity<?> modifyMobile(@RequestHeader("access_token")String token,
                                        @RequestParam("mobile") String mobile){
        return myPageService.modifyMobile(token, mobile);
    }

    @PutMapping("/modifyName")
    public ResponseEntity<?> modifyName(@RequestHeader("access_token")String token,
                                        @RequestParam("name") String name){
        return myPageService.modifyName(token, name);
    }
    @PutMapping("/modifyGender")
    public ResponseEntity<?> modifyName(@RequestHeader("access_token")String token,
                                        @RequestParam("gender") char gender){
        return myPageService.modifyGender(token, gender);
    }

    @GetMapping("/getMyBoard")
    public MyBoardDto getMyBoard(@RequestHeader("access_token") String token){
        return findMyBoardService.getMyBoard(token);
    }

    @GetMapping("/getMyLikeBoard")
    public List<Object>  getMyLikeBoard(@RequestHeader("access_token") String token){
        return findMyBoardService.getLikeBoard(token);
    }

    @GetMapping("/myScrapAnimal")
    public ResponseEntity<?> getMyScrapAnimals(@RequestHeader("access_token") String token){
        List<AnimalScrap> getMyScrapAnimals = myPageService.getMyScrapAnimals(token);
        List<MyScrapAnimalResponseDTO> responseList = myPageService.myScrapAnimalResponse(getMyScrapAnimals);
        return ResponseEntity.status(200).body(responseList);
    }
}
