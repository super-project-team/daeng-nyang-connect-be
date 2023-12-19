package com.git.backend.daeng_nyang_connect.chat;

import com.git.backend.daeng_nyang_connect.stomp.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomName(String roomName);
}
