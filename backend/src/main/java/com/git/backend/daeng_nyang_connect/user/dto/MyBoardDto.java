package com.git.backend.daeng_nyang_connect.user.dto;

import com.git.backend.daeng_nyang_connect.lost.board.entity.Lost;
import com.git.backend.daeng_nyang_connect.mate.board.dto.MateDTO;
import com.git.backend.daeng_nyang_connect.mate.board.entity.Mate;
import com.git.backend.daeng_nyang_connect.mypet.board.dto.MyPetDTO;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import com.git.backend.daeng_nyang_connect.review.board.dto.request.ReviewRequestDTO;
import com.git.backend.daeng_nyang_connect.review.board.entity.Review;
import com.git.backend.daeng_nyang_connect.tips.board.dto.TipsBoardDto;
import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBoardDto {

    private List<TipsBoardDto> tipsTitle;
    private List<MateDTO> mateTitle;
    private List<Lost> lostTitle;
    private List<MyPetDTO> myPetTitle;
    private List<ReviewRequestDTO> reviewTitle;

    public static MyBoardDto fromEntity(List<TipsBoardDto> tips, List<MateDTO> mates,  List<ReviewRequestDTO> reviewList){
        return MyBoardDto.builder()
                .tipsTitle(tips)
                .mateTitle(mates)
                .reviewTitle(reviewList)
                .build();
    }




}
