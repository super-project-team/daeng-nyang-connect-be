package com.git.backend.daeng_nyang_connect.mypet.board.repository;

import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPetRepository extends JpaRepository<MyPet, Long> {
    List<MyPet> findByUserEmail(String userEmail);

    Page<MyPet> findByTextContaining(String keyword, Pageable pageable);
}
