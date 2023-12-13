package com.git.backend.daeng_nyang_connect.user.service;

import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.lost.board.dto.LostBoardDTO;
import com.git.backend.daeng_nyang_connect.lost.board.entity.Lost;
import com.git.backend.daeng_nyang_connect.lost.board.repository.LostRepository;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import com.git.backend.daeng_nyang_connect.mate.board.repository.MateRepository;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import com.git.backend.daeng_nyang_connect.mypet.board.repository.MyPetRepository;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.review.board.repository.ReviewRepository;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.repository.TipsBoardRepository;
import com.git.backend.daeng_nyang_connect.user.dto.MyBoardDto;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Cacheable
public class FindMyBoardService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TipsBoardRepository tipsBoardRepository;
    private final LostRepository lostRepository;
    private final ReviewRepository reviewRepository;
    private final MyPetRepository myPetRepository;
    private final MateRepository mateRepository;
    private final AnimalRepository animalRepository;

    /**
     *
     *  내가 쓴 커뮤니티 글을 조회하는 API
     * @param token
     * @param myBoardDto
     * @return
     */

    public MyBoardDto getMyBoard(String token){
        User user = userService.checkUserByToken(token);

        List<Tips> tips = tipsBoardRepository.findByUser(user);
        List<Lost> lost = lostRepository.findByUser(user);
        List<Review> review = reviewRepository.findByUserNickname(user.getNickname());
        List<MyPet> myPets = myPetRepository.findByUserEmail(user.getEmail());
        List<Mate> mates = mateRepository.findByUserEmail(user.getEmail());


        List<TipsBoardDto> tipsBoardDtoList = tips.stream().
                map(title -> TipsBoardDto.builder()
                        .title(title.getTitle())
                        .build())
                .toList();

        List<ReviewRequestDTO> reviewRequestDTOS = tips.stream().
                map(title -> ReviewRequestDTO.builder()
                        .textReview(title.getTitle())
                        .build())
                .toList();

        List<MyPetDTO> myPetDTOList = tips.stream().
                map(title -> MyPetDTO.builder()
                        .text(title.getTitle())
                        .build())
                .toList();

        List<MateDTO> mateDTOList = myPetDTOList.stream()
                .map(title -> MateDTO.builder()
                        .text(title.getText()).build()).toList();

        return MyBoardDto.fromEntity(tipsBoardDtoList, mateDTOList, reviewRequestDTOS);
    }
}
