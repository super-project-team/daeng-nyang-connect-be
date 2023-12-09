package com.git.backend.daeng_nyang_connect.user.service;

import com.git.backend.daeng_nyang_connect.user.dto.MyPageDto;
import com.git.backend.daeng_nyang_connect.user.entity.MyPage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.MyPageRepository;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public MyPageDto getMyInfo(String token){
        User user = userService.checkUserByToken(token);
        MyPage my = myPageRepository.findByUser(user);

        return MyPageDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .mobile(user.getMobile())
                .nickName(user.getNickname())
                .city(user.getCity())
                .town(user.getTown())
                .experience(user.getExperience())
                .info(my.getInfo())
                .img(my.getImg())
                .gender(user.getGender())
                .build();
    }

    

}
