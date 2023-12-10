package com.git.backend.daeng_nyang_connect.mypet.board.repository;

import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPetBoardLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPetBoardLikeRepository extends JpaRepository<MyPetBoardLike, Long> {

    void deleteByUser(User user);

    Optional<MyPetBoardLike> findByUser(User user);
}
