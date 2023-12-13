package com.git.backend.daeng_nyang_connect.mypet.board.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetBoardLikeDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetResponseDTO;
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

    private static final String MSG_USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    private static final String MSG_BOARD_NOT_FOUND = "게시물을 찾을 수 없습니다.";
    private static final String MSG_OWNER_ACCESS_DENIED = "게시물의 소유자가 아닙니다.";

    public Page<MyPetResponseDTO> findAllMyPet(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<MyPet> myPetPage = myPetRepository.findAll(customPageable);
        return myPetPage.map(this::convertToMyPetResponseDTO);
    }

    public MyPetResponseDTO getThisBoard(Long myPet) {

        MyPet thisBoard = myPetRepository.findById(myPet)
                .orElseThrow(() -> new NoSuchElementException(MSG_BOARD_NOT_FOUND));

        return convertToMyPetResponseDTO(thisBoard);
    }

    private MyPetResponseDTO convertToMyPetResponseDTO(MyPet myPet) {

        List<Long> images = myPet.getImg().stream()
                .map(MyPetImage::getMyPetImgId)
                .collect(Collectors.toList());

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
                .userThumbnail(myPet.getUser().getMyPage().getImg())
                .kind(myPet.getKind())
                //     .breed(myPet.getBreed())
                .text(myPet.getText())
                .img(imgUrls)
                .myPetImgId(images)
                .createdAt(myPet.getCreatedAt())
                .comments(comments)
                .myPetLikes(likes)
                .build();
    }
    private MyPetCommentsResponseDTO convertToMyPetCommentsResponseDTO(MyPetComments comments) {
        return MyPetCommentsResponseDTO.builder()
                .myPetCommentsId(comments.getMyPetCommentsId())
                .userId(comments.getUser().getUserId())
                .nickname(comments.getUser().getNickname())
                .userThumbnail(comments.getUser().getMyPage().getImg())
                .comment(comments.getComment())
                .createdAt(comments.getCreatedAt())
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
    public Page<MyPetDTO> searchBoard(String keyword, Pageable pageable){
        Page<MyPet> myPetPage = myPetRepository.findByTextContaining(keyword, pageable);
        return myPetPage.map(myPet -> {
            String author = findUserNickNameByMyPet(myPet.getMyPetBoardId());
            return MyPetDTO.fromMyPetEntity(myPet, author);
        });
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
    public Map<String, String> postMyPet(MyPetDTO myPetDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new NoSuchElementException(MSG_USER_NOT_FOUND));

            MyPet myPet = MyPet.builder()
                    .myPetBoardId(myPetDTO.getMyPetBoardId())
                    .user(user)
                    .kind(myPetDTO.getKind())
                    .text(myPetDTO.getText())
                    .createdAt(myPetDTO.getCreatedAt())
                    .myPetLike(0)
                    .build();

            myPetRepository.save(myPet);
            myPetImgUpload.uploadMyPetImgs(myPet, myPetDTO.getText(), img);

            return createSuccessResponse("게시물이 등록되었습니다.", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return createErrorResponse("게시물 등록 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Map<String, String> modifyMyPet(Long myPetId, Long myPetImgId, MyPetDTO myPetDTO, String token, MultipartFile multipartFile)throws FileUploadFailedException {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

            MyPet myPet = myPetRepository.findById(myPetId)
                    .orElseThrow(() -> new EntityNotFoundException(MSG_BOARD_NOT_FOUND));

            checkOwnership(myPet, user);
            modifyMyPetFields(myPet, myPetDTO);
            myPetRepository.save(myPet);

            deleteMyPetImageIfRequested(myPetImgId);

            if (myPetImgId != null) {
                myPetImgUpload.uploadModifyMyPetImg(myPet, myPetDTO.getText(), multipartFile);
            } else if (multipartFile != null && !multipartFile.isEmpty()) {
                myPetImgUpload.uploadMyPetImgs(myPet, myPetDTO.getText(), Collections.singletonList(multipartFile));
            }

            return createSuccessResponse("게시물이 수정되었습니다.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_BOARD_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    private void deleteMyPetImageIfRequested(Long myPetImgId) {
        if (myPetImgId != null) {
            myPetImgUpload.deleteMyPetImg(myPetImgId);
        }
    }

    @Transactional
    public Map<String, String> deleteMyPet(Long myPetId, String token) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

            MyPet myPet = myPetRepository.findById(myPetId)
                    .orElseThrow(() -> new EntityNotFoundException(MSG_BOARD_NOT_FOUND));

            checkOwnership(myPet, user);
            myPetRepository.delete(myPet);

            return createSuccessResponse("게시물이 삭제되었습니다.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_BOARD_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public void setHeart(MyPet myPet, User user, Boolean msg) {
        boolean hasUserLiked = myPetBoardLikeRepository.findByMyPetAndUser(myPet, user).isPresent();

        if (msg) {
            // 좋아요 추가
            if (!hasUserLiked) {
                MyPetBoardLike myPetBoardLike = new MyPetBoardLike(myPet, user);
                myPet.getMyPetLikes().add(myPetBoardLike);
                myPet.setMyPetLike(myPet.getMyPetLike() + 1);
                myPetRepository.save(myPet);
            }
        } else {
            // 좋아요 취소
            if (hasUserLiked) {
                MyPetBoardLike userLike = myPetBoardLikeRepository.findByMyPetAndUser(myPet, user)
                        .orElseThrow(() -> new RuntimeException("사용자의 좋아요가 해당 게시글에 없습니다."));
                myPet.getMyPetLikes().remove(userLike);
                myPetBoardLikeRepository.delete(userLike);
                myPet.setMyPetLike(myPet.getMyPetLike() - 1);
                myPetRepository.save(myPet);
            }
        }
    }

    @Transactional
    public Map<String, String> clickLike(Long myPetId, String token) {
        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

        MyPet myPet = myPetRepository.findById(myPetId)
                .orElseThrow(() -> new EntityNotFoundException(MSG_BOARD_NOT_FOUND));

        // 이미 좋아요를 눌렀는지 확인
        boolean hasUserLiked = myPetBoardLikeRepository.findByMyPetAndUser(myPet, user).isPresent();

        if (!hasUserLiked) {
            // 좋아요 추가
            setHeart(myPet, user, true);
            return createSuccessResponse(myPetId + "번 게시글에 좋아요가 추가되었습니다.", HttpStatus.OK);
        } else {
            // 좋아요 취소
            setHeart(myPet, user, false);
            return createSuccessResponse(myPetId + "번 게시글에 좋아요가 취소되었습니다.", HttpStatus.OK);
        }
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

    private void modifyMyPetFields(MyPet myPet, MyPetDTO myPetDTO) {
        myPet.setKind(myPetDTO.getKind());
        // myPet.setBreed(myPetDTO.getBreed());
        myPet.setText(myPetDTO.getText());
    }

}
