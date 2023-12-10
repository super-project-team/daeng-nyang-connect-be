package com.git.backend.daeng_nyang_connect.mate.board.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateBoardLikeDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateResponseDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.UpdateMateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import com.git.backend.daeng_nyang_connect.mate.board.entity.MateBoardLike;
import com.git.backend.daeng_nyang_connect.mate.board.entity.MateImage;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateBoardLikeRepository;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateRepository;
import com.git.backend.daeng_nyang_connect.mate.comments.dto.MateCommentsLikeDTO;
import com.git.backend.daeng_nyang_connect.mate.comments.dto.MateCommentsResponseDTO;
import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateComments;
import com.git.backend.daeng_nyang_connect.mate.comments.entity.MateCommentsLike;
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
public class MateService {
    private final TokenProvider tokenProvider;
    private final MateRepository mateRepository;
    private final MateImgUpload mateImgUpload;
    private final UserRepository userRepository;
    private final MateBoardLikeRepository mateBoardLikeRepository;

    private static final String MSG_USER_NOT_FOUND = "유저를 찾을 수 없습니다";
    private static final String MSG_BOARD_NOT_FOUND = "게시물을 찾을 수 없습니다";
    private static final String MSG_OWNER_ACCESS_DENIED = "게시물 소유자가 아닙니다";

    public Page<MateResponseDTO> findAllMates(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), 12, pageable.getSort());
        Page<Mate> matePage = mateRepository.findAll(customPageable);
        return matePage.map(this::convertToMateResponseDTO);
    }

    public List<MateResponseDTO> findUserMates(String token) {
        String userEmail = tokenProvider.getEmailBytoken(token);

        if (userEmail == null) {
            return Collections.emptyList();
        }

        List<Mate> userMates = mateRepository.findByUserEmail(userEmail);

        return userMates.stream()
                .map(this::convertToMateResponseDTO)
                .collect(Collectors.toList());
    }

    private MateResponseDTO convertToMateResponseDTO(Mate mate) {
        // 이미지 URL 리스트 생성
        List<String> imgUrls = mate.getImg().stream()
                .map(MateImage::getUrl)
                .collect(Collectors.toList());

        // 댓글 정보 생성
        List<MateCommentsResponseDTO> comments = mate.getComment().stream()
                .map(this::convertToMateCommentsResponseDTO)
                .collect(Collectors.toList());

        // 좋아요 정보 생성
        List<MateBoardLikeDTO> likes = mate.getMateLikes().stream()
                .map(like -> MateBoardLikeDTO.builder()
                        .mateBoardLikeId(like.getMateBoardLikeId())
                        .userId(like.getUser().getUserId())
                        .build())
                .collect(Collectors.toList());

        // MateResponseDTO 생성 및 반환
        return MateResponseDTO.builder()
                .mateBoardId(mate.getMateBoardId())
                .userId(mate.getUser().getUserId())
                .nickname(mate.getUser().getNickname())
                .category(mate.getCategory())
                .img(imgUrls)
                .place(mate.getPlace())
                .createdAt(mate.getCreatedAt())
                .text(mate.getText())
                .comments(comments)
                .mateLikes(likes)
                .build();
    }

    private MateCommentsResponseDTO convertToMateCommentsResponseDTO(MateComments comments) {
        return MateCommentsResponseDTO.builder()
                .mateCommentsId(comments.getMateCommentsId())
                .userId(comments.getUser().getUserId())
                .nickname(comments.getUser().getNickname())
                .comment(comments.getComment())
                .createdAt(comments.getCreatedAt())
                .mateCommentsLike(comments.getMateCommentsLike())
                .mateCommentsLikes(convertToMateCommentsLikeDTOList(comments.getMateCommentsLikes()))
                .build();
    }

    private List<MateCommentsLikeDTO> convertToMateCommentsLikeDTOList(List<MateCommentsLike> mateCommentsLikes) {
        return mateCommentsLikes.stream()
                .map(like -> MateCommentsLikeDTO.builder()
                        .mateCommentsLikeId(like.getMateCommentsLikeId())
                        .userId(like.getUser().getUserId())
                        .build())
                .collect(Collectors.toList());
    }
    public List<MateDTO> searchBoard(String keyword){

        List<Mate> findByTextContaining = mateRepository.findByTextContaining(keyword);
        return findByTextContaining.stream().map(mate -> {
            String author = findUserNickNameByMate(mate.getMateBoardId());
            return MateDTO.fromMateEntity(mate, author);
        }).collect(Collectors.toList());

    }

    private String findUserNickNameByMate(Long mateBoardId) {
        Mate mate = mateRepository.findById(mateBoardId).orElse(null);
        if (mate != null && mate.getUser() != null) {
            return mate.getUser().getNickname();
        } else {
            return null;
        }
    }

    @Transactional
    public Map<String, String> uploadMate(MateDTO mateDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new NoSuchElementException(MSG_USER_NOT_FOUND));

            Mate mate = Mate.builder()
                    .mateBoardId(mateDTO.getMateBoardId())
                    .user(user)
                    .category(mateDTO.getCategory())
                    .place(mateDTO.getPlace())
                    .text(mateDTO.getText())
                    .createdAt(mateDTO.getCreatedAt())
                    .mateLike(0)
                    .build();

            mateRepository.save(mate);
            mateImgUpload.uploadMateImgs(mate, mateDTO.getText(), img);

            return createSuccessResponse("게시물이 등록 되었습니다", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return createErrorResponse("게시물 등록 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Map<String, String> updateMate(UpdateMateDTO updateMateDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

            Mate mate = mateRepository.findById(updateMateDTO.getMateBoardId())
                    .orElseThrow(() -> new EntityNotFoundException(MSG_BOARD_NOT_FOUND));

            checkOwnership(mate, user);
            updateMateFields(mate, updateMateDTO);
            mateRepository.save(mate);

            if (img != null && !img.isEmpty()) {
                mateImgUpload.uploadMateImgs(mate, updateMateDTO.getText(), img);
            }

            return createSuccessResponse("게시물이 업데이트 되었습니다", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    public Map<String, String> deleteMate(Long mateBoardId, String token) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new RuntimeException(MSG_USER_NOT_FOUND));

            Mate mate = mateRepository.findById(mateBoardId)
                    .orElseThrow(() -> new RuntimeException(MSG_BOARD_NOT_FOUND));

            checkOwnership(mate, user);
            mateRepository.delete(mate);

            return createSuccessResponse("게시물이 삭제되었습니다", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return createErrorResponse(MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return createErrorResponse(MSG_OWNER_ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public void setHeart(Mate mate, User user, Integer likeCount, Boolean msg) {
        if (msg) {
            MateBoardLike mateBoardLike = new MateBoardLike(mate, user);
            likeCount++;
            mate.setMateLike(likeCount);
            mateBoardLikeRepository.save(mateBoardLike);
            mateRepository.save(mate);
        } else {
            mateBoardLikeRepository.deleteByUser(user);
            likeCount--;
            mate.setMateLike(likeCount);
            mateRepository.save(mate);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> clickLike(Long mate, String token) {
        User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                .orElseThrow(() -> new EntityNotFoundException(MSG_USER_NOT_FOUND));

        Mate isMate = mateRepository.findById(mate)
                .orElseThrow();

        Map<String, String> response = new HashMap<>();

        if (mateBoardLikeRepository.findByUser(user).isEmpty()) {
            setHeart(isMate, user, isMate.getMateLike(), true);
            response.put("message", mate + "번 게시글에 좋아요가 추가 되었습니다");
        } else {
            setHeart(isMate, user, isMate.getMateLike(), false);
            response.put("message", mate + "번 게시글에 좋아요가 취소 되었습니다");
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

    private void checkOwnership(Mate mate, User user) {
        if (!mate.getUser().equals(user)) {
            throw new AccessDeniedException(MSG_OWNER_ACCESS_DENIED);
        }
    }

    private void updateMateFields(Mate mate, UpdateMateDTO updateMateDTO) {
        mate.setCategory(updateMateDTO.getCategory());
        mate.setPlace(updateMateDTO.getPlace());
        mate.setText(updateMateDTO.getText());
    }

}
