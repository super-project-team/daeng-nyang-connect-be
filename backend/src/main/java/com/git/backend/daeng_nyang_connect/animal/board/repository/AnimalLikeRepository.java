package com.git.backend.daeng_nyang_connect.animal.board.repository;

import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalLikeRepository extends JpaRepository<AnimalLike, Long> {

//    좋아요 수 합산
    @Query("SELECT count(al) FROM AnimalLike al WHERE al.animal.animalId =: animalId")
    List<AnimalLike> totalAnimalLike(@Param("animalId")Long animalId);
}
