package com.git.backend.daeng_nyang_connect.animal.dto.response;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptionStatus;
import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.AnimalImage;
import com.git.backend.daeng_nyang_connect.animal.entity.Kind;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponseDTO {
    private String userNickname;
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
    private AdoptionStatus adoptionStatus; // 입양 완료 여부
    private String createdAt;
    private String userThumbnail;
    private List<String> images = new ArrayList<>();

    public AnimalResponseDTO(Animal animal, List<AnimalImage> animalImages) {
        this.userNickname = animal.getUser().getNickname();
        this.animalName = animal.getAnimalName();
        this.age = animal.getAge();
        this.gender = animal.getGender();
        this.disease = animal.getDisease();
        this.training = animal.getTraining();
        this.neutering = animal.getNeutering();
        this.textReason = animal.getTextReason();
        this.healthCheck = animal.getHealthCheck();
        this.kind = animal.getKind();
        this.breed = animal.getBreed();
        this.nurturePeriod = animal.getNurturePeriod();
        this.city = animal.getCity();
        this.adoptionStatus = animal.getAdoptionStatus();
        this.createdAt = TimestampToFormattedString(animal.getCreatedAt());
        this.userThumbnail = animal.getUser().getMyPage().getImg();

        for (AnimalImage animalImage : animalImages) {
            this.images.add(animalImage.getUrl());
        }
    }

    public String TimestampToFormattedString(Timestamp time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return dateFormat.format(time);
    }
}
