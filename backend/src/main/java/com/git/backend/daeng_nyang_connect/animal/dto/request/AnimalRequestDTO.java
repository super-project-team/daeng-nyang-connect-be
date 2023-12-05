package com.git.backend.daeng_nyang_connect.animal.dto.request;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;
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
    private String training;
    private Boolean neutering;
    private String textReason;
    private String healthCheck;
    private Kind kind;
    private String breed;
    private Date nurturePeriod;
    private List<AnimalImage> images;

    public static Animal addToEntity(AnimalRequestDTO animalRequestDTO, User user, Timestamp createdAt) {
        return Animal.builder()
                    .user(user)
                    .animalName(animalRequestDTO.getAnimalName())
                    .age(animalRequestDTO.getAge())
                    .gender(animalRequestDTO.getGender())
                    .disease(animalRequestDTO.getDisease())
                    .training(animalRequestDTO.getTraining())
                    .neutering(animalRequestDTO.getNeutering())
                    .healthCheck(animalRequestDTO.getHealthCheck())
                    .breed(animalRequestDTO.getBreed())
                    .kind(animalRequestDTO.getKind())
                    .nurturePeriod(animalRequestDTO.getNurturePeriod())
                    .adoptionStatus(AdoptionStatus.PROGRESS)
                    .createdAt(createdAt)
                    .build();
    }

    public static Animal updateToDTO(UpdateAnimalRequestDTO updateAnimalRequestDTO, Animal animal) {
        return Animal.builder()
                .user(animal.getUser())
                .animalName(updateAnimalRequestDTO.getAnimalName())
                .age(updateAnimalRequestDTO.getAge())
                .gender(updateAnimalRequestDTO.getGender())
                .disease(updateAnimalRequestDTO.getDisease())
                .training(updateAnimalRequestDTO.getTraining())
                .neutering(updateAnimalRequestDTO.getNeutering())
                .healthCheck(updateAnimalRequestDTO.getHealthCheck())
                .breed(updateAnimalRequestDTO.getBreed())
                .kind(updateAnimalRequestDTO.getKind())
                .nurturePeriod(updateAnimalRequestDTO.getNurturePeriod())
                .adoptionStatus(AdoptionStatus.PROGRESS)
                .createdAt(animal.getCreatedAt())
                .build();
    }


}
