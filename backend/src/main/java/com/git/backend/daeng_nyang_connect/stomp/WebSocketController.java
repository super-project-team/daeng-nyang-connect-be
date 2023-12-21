package com.git.backend.daeng_nyang_connect.stomp;

import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Cacheable
public class WebSocketController {

    @MessageMapping("/sendMessage") // 클라이언트에서 메시지를 보내는 엔드포인트
    @SendTo("/topic/messages") // 메시지를 발행할 토픽
    public MessageDTO processMessageFromClient(@RequestHeader("access_token") String token,
                                               MessageDTO message) throws Exception {
        // 받은 메시지를 처리하는 비즈니스 로직을 작성
        // 여기서는 간단히 받은 메시지를 다시 클라이언트로 보내는 예제
        return message;
    }
}

//@Controller
//@RequiredArgsConstructor
//@Cacheable
//public class WebSocketController {
//
//    private final ChatService chatService;
//
//    @MessageMapping("/sendChatRequest")
//    @SendTo("/topic/chatRequests")
//    public ResponseEntity<?> sendChatRequest(@RequestHeader("access_token") String token,
//                                             @RequestParam("receiverUsername") String receiverUsername) {
//        // 채팅을 원하는 사람에게 채팅을 원한다는 요청 처리 로직
//        chatService.sendChatRequest(receiverUsername, token);
//        return ResponseEntity.status(200).body("채팅 요청이 전송되었습니다.");
//    }
//
//    @MessageMapping("/acceptChatRequest")
//    @SendTo("/topic/chatRooms")
//    public ResponseEntity<?> acceptChatRequest(@RequestHeader("access_token") String token,
//                                               @RequestParam("receiverUsername") String receiverUsername) {
//        // 채팅을 수락하여 채팅방을 만드는 로직
//        // 채팅방 정보(ChatRoom)를 클라이언트에게 전송
//        chatService.addChatRoom(receiverUsername, token);
//        return ResponseEntity.status(200).body("채팅이 수락되었습니다.");
//    }
//
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/chat/{roomId}")
//    public ResponseEntity<?> sendMessage(@RequestHeader("access_token") String token,
//                                         @RequestBody MessageDTO message) {
//        // 채팅방에 메세지를 보내는 로직
//        // 메세지 정보(ChatMessage)를 클라이언트에게 전송
//        chatService.handleChatMessage(message, token);
//        return ResponseEntity.status(200).body("메시지가 전송되었습니다.");
//    }
//}