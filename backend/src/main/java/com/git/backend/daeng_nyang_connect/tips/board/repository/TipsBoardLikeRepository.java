package com.git.backend.daeng_nyang_connect.tips.board.repository;

import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsBoardLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipsBoardLikeRepository extends JpaRepository<TipsBoardLike, Long> {
    void deleteByUser(User user);
}
