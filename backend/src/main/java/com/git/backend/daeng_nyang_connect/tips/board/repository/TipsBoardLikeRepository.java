package com.git.backend.daeng_nyang_connect.tips.board.repository;

import com.git.backend.daeng_nyang_connect.tips.board.entity.Tips;
import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsBoardLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipsBoardLikeRepository extends JpaRepository<TipsBoardLike, Long> {
    void deleteByUser(User user);

    Optional<TipsBoardLike> findByUser(User user);

    List<TipsBoardLike> findByTips(Tips thisBoard);
}

