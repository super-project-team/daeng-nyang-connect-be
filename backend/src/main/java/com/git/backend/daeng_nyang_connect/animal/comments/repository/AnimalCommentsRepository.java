package com.git.backend.daeng_nyang_connect.animal.comments.repository;

import com.git.backend.daeng_nyang_connect.animal.board.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.comments.entity.AnimalComments;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalCommentsRepository extends JpaRepository<AnimalComments, Long> {

    List<AnimalComments> findByAnimal(Animal animal);
}
