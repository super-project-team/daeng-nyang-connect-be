//package com.git.backend.daeng_nyang_connect.stomp;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//public class WebSocketController {
//
//    private final SimpMessageSendingOperations sendingOperations;
//
//    @MessageMapping("/chat/message")
//    public void enter(MessageDTO message) {
//        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
//    }
//}