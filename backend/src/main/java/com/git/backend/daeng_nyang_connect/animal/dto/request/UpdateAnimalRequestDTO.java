package com.git.backend.daeng_nyang_connect.animal.dto.request;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnimalRequestDTO {
    private Long animalId;
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

    public void checkUpdateList(UpdateAnimalRequestDTO updateAnimalRequestDTO, Animal animal) {
        if (Objects.isNull(updateAnimalRequestDTO.getAnimalName())) {
            this.animalName = animal.getAnimalName();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getAge())) {
            this.age = animal.getAge();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getGender())) {
            this.gender = animal.getGender();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getDisease())) {
            this.disease = animal.getDisease();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getNurturePeriod())) {
            this.nurturePeriod = animal.getNurturePeriod();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getTraining())) {
            this.training = animal.getTraining();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getNeutering())) {
            this.neutering = animal.getNeutering();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getTextReason())) {
            this.textReason = animal.getTextReason();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getKind())) {
            this.kind = animal.getKind();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getBreed())) {
            this.breed = animal.getBreed();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getHealthCheck())) {
            this.healthCheck = animal.getHealthCheck();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getImages())) {
            this.images = animal.getImages();
        }
    }
}
