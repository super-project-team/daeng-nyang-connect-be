package com.git.backend.daeng_nyang_connect.animal.board.repository;

import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
