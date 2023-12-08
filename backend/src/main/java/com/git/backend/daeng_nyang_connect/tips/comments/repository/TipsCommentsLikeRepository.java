package com.git.backend.daeng_nyang_connect.tips.comments.repository;

import com.git.backend.daeng_nyang_connect.tips.comments.entity.TipsCommentsLike;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface TipsCommentsLikeRepository extends JpaRepository<TipsCommentsLike, Long> {
    void deleteByUser(User user);

   Optional<TipsCommentsLike> findByUser(User user);
}
