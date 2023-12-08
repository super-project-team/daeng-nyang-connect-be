package com.git.backend.daeng_nyang_connect.animal.dto.request;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private String city;

    public void checkUpdateList(AnimalRequestDTO animalRequestDTO, Animal animal) {
        if (Objects.isNull(animalRequestDTO.getAnimalName())) {
            this.animalName = animal.getAnimalName();
        }

        if (Objects.isNull(animalRequestDTO.getAge())) {
            this.age = animal.getAge();
        }

        if (Objects.isNull(animalRequestDTO.getGender())) {
            this.gender = animal.getGender();
        }

        if (Objects.isNull(animalRequestDTO.getDisease())) {
            this.disease = animal.getDisease();
        }

        if (Objects.isNull(animalRequestDTO.getNurturePeriod())) {
            this.nurturePeriod = animal.getNurturePeriod();
        }

        if (Objects.isNull(animalRequestDTO.getTraining())) {
            this.training = animal.getTraining();
        }

        if (Objects.isNull(animalRequestDTO.getNeutering())) {
            this.neutering = animal.getNeutering();
        }

        if (Objects.isNull(animalRequestDTO.getTextReason())) {
            this.textReason = animal.getTextReason();
        }

        if (Objects.isNull(animalRequestDTO.getKind())) {
            this.kind = animal.getKind();
        }

        if (Objects.isNull(animalRequestDTO.getBreed())) {
            this.breed = animal.getBreed();
        }

        if (Objects.isNull(animalRequestDTO.getHealthCheck())) {
            this.healthCheck = animal.getHealthCheck();
        }

        if (Objects.isNull(animalRequestDTO.getCity())) {
            this.city = animal.getCity();
        }
    }
}
