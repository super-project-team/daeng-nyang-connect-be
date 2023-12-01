package com.git.backend.daeng_nyang_connect.animal.board.dto.request;

import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
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
    private String animalName;
    private Integer age;
    private String gender;
    private String disease;
    private Date adoptionDate;
    private String training;
    private String neutering;
    private String contents;
    private String healthCheck;
    private List<AnimalImage> images;

    public UpdateAnimalRequestDTO(Animal animal, UpdateAnimalRequestDTO updateAnimalRequestDTO) {
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

        if (Objects.isNull(updateAnimalRequestDTO.getAdoptionDate())) {
            this.adoptionDate = animal.getAdoptionDate();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getTraining())) {
            this.training = animal.getTraining();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getNeutering())) {
            this.neutering = animal.getNeutering();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getContents())) {
            this.contents = animal.getContents();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getHealthCheck())) {
            this.healthCheck = animal.getHealthCheck();
        }

        if (Objects.isNull(updateAnimalRequestDTO.getImages())) {
            this.images = animal.getImages();
        }
    }
}
