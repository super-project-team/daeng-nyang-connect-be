package com.git.backend.daeng_nyang_connect.mypet.board.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetBoardLikeDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetResponseDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.UpdateMyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPetBoardLike;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPetImage;
import com.git.backend.daeng_nyang_connect.mypet.board.repository.MyPetBoardLikeRepository;
import com.git.backend.daeng_nyang_connect.mypet.board.repository.MyPetRepository;
import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsLikeDTO;
import com.git.backend.daeng_nyang_connect.mypet.comments.dto.MyPetCommentsResponseDTO;
import com.git.backend.daeng_nyang_connect.mypet.comments.entity.MyPetComments;
import com.git.backend.daeng_nyang_connect.mypet.comments.entity.MyPetCommentsLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Cacheable
public class MyPetService {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final MyPetRepository myPetRepository;
    private final MyPetImgUpload myPetImgUpload;
    private final MyPetBoardLikeRepository myPetBoardLikeRepository;

    private static final String MSG_USER_NOT_FOUND = "유저를 찾을 수 없습니다";
    private static final String MSG_BOARD_NOT_FOUND = "게시물을 찾을 수 없습니다";
    private static final String MSG_OWNER_ACCESS_DENIED = "게시물 소유자가 아닙니다";

    public Page<MyPetResponseDTO> findAllMyPet(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        Page<MyPet> myPetPage = myPetRepository.findAll(customPageable);
        return myPetPage.map(this::convertToMyPetResponseDTO);
    }

    public List<MyPetResponseDTO> findUserMyPet(String token) {
        String userEmail = tokenProvider.getEmailBytoken(token);

        if (userEmail == null) {
            return Collections.emptyList();
        }

        List<MyPet> userMyPet = myPetRepository.findByUserEmail(userEmail);

        return userMyPet.stream()
                .map(this::convertToMyPetResponseDTO)
                .collect(Collectors.toList());
    }

    private MyPetResponseDTO convertToMyPetResponseDTO(MyPet myPet) {
        List<String> imgUrls = myPet.getImg().stream()
                .map(MyPetImage::getUrl)
                .collect(Collectors.toList());

        List<MyPetCommentsResponseDTO> comments = myPet.getComment().stream()
                .map(this::convertToMyPetCommentsResponseDTO)
                .collect(Collectors.toList());

        List<MyPetBoardLikeDTO> likes = myPet.getMyPetLikes().stream()
                .map(like -> MyPetBoardLikeDTO.builder()
                        .myPetBoardLikeId(like.getMyPetBoardLikeId())
                        .userId(like.getUser().getUserId())
                        .build())
                .collect(Collectors.toList());

        return MyPetResponseDTO.builder()
                .myPetBoardId(myPet.getMyPetBoardId())
                .userId(myPet.getUser().getUserId())
                .nickname(myPet.getUser().getNickname())
                .kind(myPet.getKind())
                .breed(myPet.getBreed())
                .text(myPet.getText())
                .createdAt(myPet.getCreatedAt())
                .img(imgUrls)
                .comments(comments)
                .myPetLikes(likes)
                .build();
    }
    private MyPetCommentsResponseDTO convertToMyPetCommentsResponseDTO(MyPetComments comments) {
        return MyPetCommentsResponseDTO.builder()
                .myPetCommentsId(comments.getMyPetCommentsId())
                .userId(comments.getUser().getUserId())
                .nickname(comments.getUser().getNickname())
                .comment(comments.getComment())
                .createdAt(comments.getCreatedAt())
                .myPetCommentsLike(comments.getMyPetCommentsLike())
                .myPetCommentsLikes(convertToMyPetCommentsLikeDTOList(comments.getMyPetCommentsLikes()))
                .build();
    }

    private List<MyPetCommentsLikeDTO> convertToMyPetCommentsLikeDTOList(List<MyPetCommentsLike> myPetCommentsLikes) {
        return myPetCommentsLikes.stream()
                .map(like -> MyPetCommentsLikeDTO.builder()
                        .myPetCommentsLikeId(like.getMyPetCommentsLikeId())
                        .userId(like.getUser().getUserId())
                        .build())
                .collect(Collectors.toList());
    }
    public List<MyPetDTO> searchBoard(String keyword){

        List<MyPet> findByTextContaining = myPetRepository.findByTextContaining(keyword);
        return findByTextContaining.stream().map(myPet -> {
            String author = findUserNickNameByMyPet(myPet.getMyPetBoardId());
            return MyPetDTO.fromMyPetEntity(myPet, author);
        }).collect(Collectors.toList());

    }

    private String findUserNickNameByMyPet(Long myPetBoardId) {
        MyPet myPet = myPetRepository.findById(myPetBoardId).orElse(null);
        if (myPet != null && myPet.getUser() != null) {
            return myPet.getUser().getNickname();
        } else {
            return null;
        }
    }

    @Transactional
    public Map<String, String> uploadMyPet(MyPetDTO myPetDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new NoSuchElementException(MSG_USER_NOT_FOUND));

            MyPet myPet = MyPet.builder()
                    .myPetBoardId(myPetDTO.getMyPetBoardId())
                    .user(user)
                    .kind(myPetDTO.getKind())
                    .breed(myPetDTO.getBreed())
                    .text(myPetDTO.getText())
                    .createdAt(myPetDTO.getCreatedAt())
                    .myPetLike(0)
                    .build();

            myPetRepository.save(myPet);
            myPetImgUpload.uploadMyPetImgs(myPet, myPetDTO.getText(), img);

            return createSuccessResponse("게시물이 등록 되었습니다", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return createErrorResponse("게시물 등록 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Map<String, String> updateMyPet(UpdateMyPetDTO updateMyPetDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

            MyPet myPet = myPetRepository.findById(updateMyPetDTO.getMyPetBoardId())
                    .orElseThrow(() -> new EntityNotFoundException(MSG_BOARD_NOT_FOUND));

            checkOwnership(myPet, user);
            updateMyPetFields(myPet, updateMyPetDTO);
            myPetRepository.save(myPet);

            if (img != null && !img.isEmpty()) {
                myPetImgUpload.uploadMyPetImgs(myPet, updateMyPetDTO.getText(), img);
            }

            return createSuccessResponse("게시물이 업데이트 되었습니다", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    public Map<String, String> deleteMyPet(Long myPetBoardId, String token) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new RuntimeException(MSG_USER_NOT_FOUND));

            MyPet myPet = myPetRepository.findById(myPetBoardId)
                    .orElseThrow(() -> new RuntimeException(MSG_BOARD_NOT_FOUND));

            checkOwnership(myPet, user);
            myPetRepository.delete(myPet);

            return createSuccessResponse("게시물이 삭제되었습니다", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public void setHeart(MyPet myPet, User user, Integer likeCount, Boolean msg) {
        if (msg) {
            MyPetBoardLike myPetBoardLike = new MyPetBoardLike(myPet, user);
            likeCount++;
            myPet.setMyPetLike(likeCount);
            myPetBoardLikeRepository.save(myPetBoardLike);
            myPetRepository.save(myPet);
        } else {
            myPetBoardLikeRepository.deleteByUser(user);
            likeCount--;
            myPet.setMyPetLike(likeCount);
            myPetRepository.save(myPet);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> clickLike(Long myPet, String token) {
        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

        MyPet isMyPet = myPetRepository.findById(myPet)
                .orElseThrow();

        Map<String, String> response = new HashMap<>();

        if (myPetBoardLikeRepository.findByUser(user).isEmpty()) {
            setHeart(isMyPet, user, isMyPet.getMyPetLike(), true);
            response.put("message", myPet + "번 게시글에 좋아요가 추가 되었습니다");
        } else {
            setHeart(isMyPet, user, isMyPet.getMyPetLike(), false);
            response.put("message", myPet + "번 게시글에 좋아요가 취소 되었습니다");
        }

        return ResponseEntity.ok(response);
    }

    private Map<String, String> createSuccessResponse(String message, HttpStatus httpStatus) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", message);
        response.put("http_status", httpStatus.toString());
        return response;
    }

    private Map<String, String> createErrorResponse(String message, HttpStatus httpStatus) {
        Map<String, String> response = new HashMap<>();
        response.put("error_msg", message);
        response.put("http_status", httpStatus.toString());
        return response;
    }

    private void checkOwnership(MyPet myPet, User user) {
        if (!myPet.getUser().equals(user)) {
            throw new AccessDeniedException(MSG_OWNER_ACCESS_DENIED);
        }
    }

    private void updateMyPetFields(MyPet myPet, UpdateMyPetDTO updateMyPetDTO) {
        myPet.setKind(updateMyPetDTO.getKind());
        myPet.setBreed(updateMyPetDTO.getBreed());
        myPet.setText(updateMyPetDTO.getText());
    }

}
