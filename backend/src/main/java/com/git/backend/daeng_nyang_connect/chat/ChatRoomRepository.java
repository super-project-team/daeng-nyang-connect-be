package com.git.backend.daeng_nyang_connect.chat;

import com.git.backend.daeng_nyang_connect.animal.board.entity.AnimalImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
