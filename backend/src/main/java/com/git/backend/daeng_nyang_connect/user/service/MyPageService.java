package com.git.backend.daeng_nyang_connect.user.service;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalScrap;
import com.git.backend.daeng_nyang_connect.animal.repository.AdoptedAnimalRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalImageRepository;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalScrapRepository;
import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.user.dto.ModifyUserDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyBoardDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyPageDto;
import com.git.backend.daeng_nyang_connect.user.dto.MyScrapAnimalResponseDTO;
import com.git.backend.daeng_nyang_connect.user.entity.MyPage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.MyPageRepository;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProfileImgService profileImgService;
    private final PasswordEncoder passwordEncoder;
    private final AnimalScrapRepository animalScrapRepository;
    private final AnimalImageRepository animalImageRepository;



    public MyPageDto getMyInfo(String token){
        User user = userService.checkUserByToken(token);
        MyPage my = myPageRepository.findByUser(user);

        return MyPageDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .mobile(user.getMobile())
                .nickname(user.getNickname())
                .city(user.getCity())
                .town(user.getTown())
                .experience(user.getExperience())
                .info(my.getInfo())
                .img(my.getImg())
                .gender(user.getGender())
                .build();
    }

    public Map<String,String> modifyProfile(String token,MultipartFile multipartFile) throws FileUploadFailedException {
        User user = userService.checkUserByToken(token);
        MyPage byUser = myPageRepository.findByUser(user);

        profileImgService.uploadUserProfile(byUser, user.getNickname(), multipartFile);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "프로필 사진이 등록 되었습니다.");
        return response;
    }

    public Map<String ,String> modifyPassword(String token, ModifyUserDto modifyUserDto){
        User user = userService.checkUserByToken(token);
        user.setPassword(passwordEncoder.encode(modifyUserDto.getPassword()));
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "비밀번호 변경이 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return response;
    }

    public Map<String ,String> modifyInfo(String token, ModifyUserDto modifyUserDto){
        User user = userService.checkUserByToken(token);
        MyPage byUser = myPageRepository.findByUser(user);

        byUser.setInfo(modifyUserDto.getInfo());

        myPageRepository.save(byUser);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "유저 정보가 변경이 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return response;
    }
    public Map<String ,String> modifyNickname(String token, ModifyUserDto modifyUserDto){
        User user = userService.checkUserByToken(token);

        user.setNickname(modifyUserDto.getNickname());

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "유저 닉네임이 변경이 되었습니다");
        response.put("http_status", HttpStatus.OK.toString());
        return response;
    }


    public List<AnimalScrap> getMyScrapAnimals(String token) {
        User user = userService.checkUserByToken(token);
        return animalScrapRepository.findByUser(user);
    }

    public List<MyScrapAnimalResponseDTO> myScrapAnimalResponse(List<AnimalScrap> getMyScrapAnimals){
        List<MyScrapAnimalResponseDTO> responseList = new ArrayList<>();
        for (AnimalScrap myScrapAnimal:getMyScrapAnimals){
            List<AnimalImage> animalImageList = animalImageRepository.findByAnimal(myScrapAnimal.getAnimal());
            responseList.add(new MyScrapAnimalResponseDTO(myScrapAnimal, animalImageList));
        }
        return responseList;
    }
}
