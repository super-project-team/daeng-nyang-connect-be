package com.git.backend.daeng_nyang_connect.animal.repository;

import com.git.backend.daeng_nyang_connect.animal.entity.AdoptedAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptedAnimalRepository extends JpaRepository<AdoptedAnimal, Long> {
}
