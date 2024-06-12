package com.git.backend.daeng_nyang_connect.animal.repository.querydsl;


import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.entity.QAnimal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.git.backend.daeng_nyang_connect.animal.entity.QAnimal.animal;

@Repository
@RequiredArgsConstructor
public class AnimalCustomRepositoryImpl implements AnimalCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Animal> findByBreed() {
        return jpaQueryFactory
                .selectFrom(animal)
                .where(animal.breed.eq("먼치킨"))
                .limit(10)
                .fetch();
    }
}
