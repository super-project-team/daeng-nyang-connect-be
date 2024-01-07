package com.git.backend.daeng_nyang_connect.stomp;

import jakarta.persistence.Cacheable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RestController
@RequiredArgsConstructor
@Cacheable
public class WebSocketController {

    private final ChatService chatService;

    @Transactional
    @MessageMapping("/sendMessage")
    @SendTo("/topic/chat/{roomId}")
    public ResponseEntity<?> sendMessage(@RequestHeader("access_token") String token,
                                            @RequestBody MessageDTO message) {
        // 채팅방에 메세지를 보내는 로직
        // 메세지 정보(ChatMessage)를 클라이언트에게 전송
        chatService.handleChatMessage(message, token);
        return ResponseEntity.status(200).body(message);
    }
}