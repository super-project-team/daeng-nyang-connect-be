package com.git.backend.daeng_nyang_connect.animal.dto.response;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalGetAllDTO {

    private Long boardId;
    private Long userId;
    private String nickname;
    private String animalName;
    private String age;
    private String gender;
    private String disease;
    private String training;
    private Boolean neutering;
    private String textReason;
    private String textEtc;
    private String healthCheck;
    private Kind kind;
    private String breed;
    private Integer  nurturePeriod;
    private String city;
    private AdoptionStatus adoptionStatus; // 입양 완료 여부
    private String createdAt;
    private List<AnimalImage> animalImageList;

    public static AnimalGetAllDTO fromEntity(Animal animal, List<AnimalImage> animalImage){
        return AnimalGetAllDTO.builder()
                .boardId(animal.getAnimalId())
                .userId(animal.getUser().getUserId())
                .nickname(animal.getUser().getNickname())
                .animalName(animal.getAnimalName())
                .age(animal.getAge())
                .gender(animal.getGender())
                .disease(animal.getDisease())
                .breed(animal.getBreed())
                .textReason(animal.getTextReason())
                .training(animal.getTraining())
                .neutering(animal.getNeutering())
                .healthCheck(animal.getHealthCheck())
                .createdAt(animal.getCreatedAt().toString())
                .nurturePeriod(animal.getNurturePeriod())
                .kind(animal.getKind())
                .textEtc(animal.getTextEtc())
                .adoptionStatus(animal.getAdoptionStatus())
                .city(animal.getCity())
                .animalImageList(animalImage)
                .build();
    }
}
