package com.git.backend.daeng_nyang_connect.animal.dto.response;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalBreedDto {

    private Long Id;
    private String name;
    private String breed;
    private String age;
    private String disease;

    public AnimalBreedDto(Animal animal) {
        Id = animal.getAnimalId();
        this.name = animal.getAnimalName();
        this.breed = animal.getBreed();
        this.age = animal.getAge();
        this.disease = animal.getDisease();
    }
}
