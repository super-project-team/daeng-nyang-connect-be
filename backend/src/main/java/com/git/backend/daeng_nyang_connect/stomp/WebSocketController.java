package com.git.backend.daeng_nyang_connect.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final ChatService chatService;

    @PostMapping("/chat/request")
    public ResponseEntity<?> requestChat(@RequestHeader("access_token") String  token,
                                         @RequestParam("receiverUsername") String receiverUsername){
        chatService.sendChatRequest(receiverUsername, token);
        return ResponseEntity.status(200).body("요청 완료");
    }

    @PostMapping("/addChatRoom")
    public ResponseEntity<?> addChatRoom(@RequestHeader("access_token") String  token,
                                         @RequestParam("receiverUsername") String receiverUsername){
        chatService.addChatRoom(receiverUsername, token);
        return ResponseEntity.status(200).body("채팅이 가능합니다.");
    }
    @MessageMapping("/chat") // 클라이언트에서 "/app/chat"으로 메시지를 보내면 이 메서드가 호출됨
    @SendTo("/topic/messages") // 이 메서드의 결과를 "/topic/messages" 토픽으로 브로드캐스트
    public ResponseEntity<?> handleChatMessage(@RequestHeader("access_token") String  token,
                                               @RequestBody MessageDTO message) {
        // 클라이언트로부터 받은 채팅 메시지를 처리하는 로직
        chatService.handleChatMessage(message, token);
        return ResponseEntity.status(200).body(message.getContent());
    }


}

