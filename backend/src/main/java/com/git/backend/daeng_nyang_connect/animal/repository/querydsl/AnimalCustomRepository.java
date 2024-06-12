package com.git.backend.daeng_nyang_connect.animal.repository.querydsl;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;

import java.util.List;

public interface AnimalCustomRepository {

    List<Animal> findByBreed();
}
