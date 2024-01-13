package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;
    private ChatService cServ = CTXProvider.ctx.getBean(ChatService.class);

//    @Transactional
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/chat/{roomId}")
//    public void sendMessage(@RequestHeader("access_token") String token,
//                                            @RequestBody MessageDTO message) {
//        // 채팅방에 메세지를 보내는 로직
//        // 메세지 정보(ChatMessage)를 클라이언트에게 전송
//        MessageDTO responseMessage = chatService.handleChatMessage(message, token);
//        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), responseMessage);
//
//        // 메시지 저장
//        Message sendMessage = Message.builder()
//                .content(message.getContent())
//                .chatRoom(chatRoomRepository.findById(message.getRoomId()).orElseThrow())
//                .sender(userRepository.findByNickname(responseMessage.getSender()))
//                .build();
//
//        messageRepository.save(sendMessage);
//    }

    @Transactional
    @MessageMapping("/sendMessage")
    @SendTo("/topic/chat/{roomId}")
    public void sendMessage(@RequestHeader("access_token") String token,
                            @RequestBody MessageDTO message) {
        // 채팅방에 메세지를 보내는 로직
        // 메세지 정보(ChatMessage)를 클라이언트에게 전송
        MessageDTO responseMessage = chatService.handleChatMessage(message, token);
        cServ.saveMessage(responseMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + responseMessage.getRoomId(), responseMessage);
    }

}