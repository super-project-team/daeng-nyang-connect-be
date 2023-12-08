package com.git.backend.daeng_nyang_connect.mate.board.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateResponseDTO;
import com.git.backend.daeng_nyang_connect.mate.board.dto.UpdateMateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import com.git.backend.daeng_nyang_connect.mate.board.entity.MateBoardLike;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateBoardLikeRepository;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateRepository;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
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


    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Page<MateResponseDTO> findAllMates(Pageable pageable) {
        Page<Mate> matePage = mateRepository.findAll(pageable);
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
        User user = mate.getUser();

        return MateResponseDTO.builder()
                .mateBoardId(mate.getMateBoardId())
                .category(mate.getCategory())
                .img(mate.getImg())
                .place(mate.getPlace())
                .createdAt(mate.getCreatedAt())
                .text(mate.getText())
                .comment(mate.getComment())
                .mateLike(mate.getMateLike())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public Map<String, String> addMate(MateDTO mateDTO, String token, List<MultipartFile> img) {
        try {
            User user = userRepository.findByEmail(tokenProvider.getEmailBytoken(token))
                    .orElseThrow(() -> new NoSuchElementException(MSG_USER_NOT_FOUND));

            Mate mate = Mate.builder()
                    .mateBoardId(mateDTO.getMateBoardId())
                    .user(user)
                    .category(mateDTO.getCategory())
                    .place(mateDTO.getPlace())
                    .text(mateDTO.getText())
                    .createdAt(timestamp)
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

    public ResponseEntity<String> clickLike(Long mate, String token) {
        String email = tokenProvider.getEmailBytoken(token);
        Optional<User> isUser = userRepository.findByEmail(email);
        Optional<Mate> isMate = mateRepository.findById(mate);

        if (isMate.isPresent() && isUser.isPresent()) {
            if (mateBoardLikeRepository.findByUser(isUser.get()).isEmpty()) {
                setHeart(isMate.get(), isUser.get(), isMate.get().getMateLike(), true);
                return ResponseEntity.ok().body(mate + "번 게시글에 좋아요가 추가 되었습니다");
            } else {
                setHeart(isMate.get(), isUser.get(), isMate.get().getMateLike(), false);
                return ResponseEntity.ok().body(mate + "번 게시글에 좋아요가 취소 되었습니다");
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
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

    private void checkOwnership(Mate mate, User user) {
        if (!mate.getUser().equals(user)) {
            throw new AccessDeniedException(MSG_OWNER_ACCESS_DENIED);
        }
    }

    private void updateMateFields(Mate mate, UpdateMateDTO updateMateDTO) {
        mate.setCategory(updateMateDTO.getCategory());
        mate.setPlace(updateMateDTO.getPlace());
        mate.setText(updateMateDTO.getText());
        mate.setCreatedAt(updateMateDTO.getCreatedAt());
        mate.setMateLike(updateMateDTO.getMateLike());
    }

}
