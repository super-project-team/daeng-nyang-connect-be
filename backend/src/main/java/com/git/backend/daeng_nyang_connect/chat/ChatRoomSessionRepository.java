package com.git.backend.daeng_nyang_connect.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomSessionRepository extends JpaRepository<ChatRoomSession, Long> {
    void deleteByWebSocketSessionId(String id);
}
