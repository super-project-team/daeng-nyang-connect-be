package com.git.backend.daeng_nyang_connect.tips.board.service;


import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.repository.TipsBoardRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Cacheable
public class TipsBoardService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TipsBoardRepository tipsBoardRepository;
    private final TipsImgUpload tipsImgUpload;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Transactional
    public Map<?,?> postBoard(TipsBoardDto tipsBoardDto, String token, List<MultipartFile> img){
        String email = tokenProvider.getEmailBytoken(token);
        Optional<User> isUser = userRepository.findByEmail(email);


        if(isUser.isPresent()){
            User user = isUser.get();

            Tips tips = Tips.builder()
                    .tipsBoardId(tipsBoardDto.getTipsBoardId())
                    .user(user)
                    .category(tipsBoardDto.getCategory())
                    .title(tipsBoardDto.getTitle())
                    .text(tipsBoardDto.getText())
                    .createdAt(timestamp)
                    .tips_like(0)
                    .build();
        tipsBoardRepository.save(tips);
        tipsImgUpload.uploadTipsImgs(tips, tipsBoardDto.getTitle(), img);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "게시물이 등록 되었습니다");
        response.put("http_status", HttpStatus.CREATED.toString());
        return response;
        }
        else{
            Map<String, String> response = new HashMap<>();
            response.put("msg","없는 유저 입니다");
            response.put("http_status", HttpStatus.NOT_FOUND.toString());
            return response;
        }
    }
        //게시글과 작성 시간 좋아요만 보이면 됨
//    @Transactional
//    public Page<Tips> getAll(Pageable pageable){
//        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), 25, pageable.getSort());
//
//        Page<Tips> tipsPage = tipsBoardRepository.findAll(customPageable);
//
//        List<Tips> tipsList = tipsPage.getContent();
//
//        return tipsList.stream()
//                .map(tips -> {
//
//                })
//    }
//







}
