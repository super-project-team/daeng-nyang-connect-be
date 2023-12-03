package com.git.backend.daeng_nyang_connect.animal.board.dto.request;

import com.git.backend.daeng_nyang_connect.animal.board.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequestDTO {
    private String animalName;
    private Integer age;
    private String gender;
    private String disease;
    private Date adoptionDate;
    private String training;
    private Boolean neutering;
    private String contents;
    private String healthCheck;
    private List<AnimalImage> images;

    public static Animal addToEntity(AnimalRequestDTO animalRequestDTO, User user) {
        return Animal.builder()
                    .user(user)
                    .nickname(user.getNickname())
                    .animalName(animalRequestDTO.getAnimalName())
                    .age(animalRequestDTO.getAge())
                    .gender(animalRequestDTO.getGender())
                    .disease(animalRequestDTO.getDisease())
                    .adoptionDate(animalRequestDTO.getAdoptionDate())
                    .training(animalRequestDTO.getTraining())
                    .neutering(animalRequestDTO.getNeutering())
                    .contents(animalRequestDTO.getContents())
                    .healthCheck(animalRequestDTO.getHealthCheck())
                    .like(0)
                    .adoptionStatus(AdoptionStatus.STILL)
                    .build();
    }

    public static Animal updateToDTO(UpdateAnimalRequestDTO updateAnimalRequestDTO, Animal animal) {
        return Animal.builder()
                .user(animal.getUser())
                .nickname(animal.getNickname())
                .animalName(updateAnimalRequestDTO.getAnimalName())
                .age(updateAnimalRequestDTO.getAge())
                .gender(updateAnimalRequestDTO.getGender())
                .disease(updateAnimalRequestDTO.getDisease())
                .adoptionDate(updateAnimalRequestDTO.getAdoptionDate())
                .training(updateAnimalRequestDTO.getTraining())
                .neutering(updateAnimalRequestDTO.getNeutering())
                .contents(updateAnimalRequestDTO.getContents())
                .healthCheck(updateAnimalRequestDTO.getHealthCheck())
                .like(animal.getLike())
                .adoptionStatus(animal.getAdoptionStatus())
                .build();
    }


}
