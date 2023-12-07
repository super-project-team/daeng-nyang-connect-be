package com.git.backend.daeng_nyang_connect.tips.board.repository;

import com.git.backend.daeng_nyang_connect.tips.board.entity.TipsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipsImageRepository extends JpaRepository<TipsImage, Long> {
}
