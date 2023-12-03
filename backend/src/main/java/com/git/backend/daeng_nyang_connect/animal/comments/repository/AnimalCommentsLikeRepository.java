package com.git.backend.daeng_nyang_connect.animal.comments.repository;

import com.git.backend.daeng_nyang_connect.animal.comments.entity.AnimalCommentsLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalCommentsLikeRepository extends JpaRepository<AnimalCommentsLike, Long> {
    Optional<AnimalCommentsLike> findByUser(User User);
}
