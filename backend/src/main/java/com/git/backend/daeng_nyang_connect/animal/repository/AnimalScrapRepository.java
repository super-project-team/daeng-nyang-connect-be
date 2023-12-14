package com.git.backend.daeng_nyang_connect.animal.repository;

import com.git.backend.daeng_nyang_connect.animal.entity.AnimalScrap;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalScrapRepository extends JpaRepository<AnimalScrap, Long> {

    List<AnimalScrap> findByUser(User user);

    void deleteByUser(User user);
}
