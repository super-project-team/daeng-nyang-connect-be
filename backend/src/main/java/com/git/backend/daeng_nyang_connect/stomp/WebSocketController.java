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
//    // "/user/{username}/queue/chat" 주제를 구독
//    stompClient.subscribe('/user/' + receiverUsername + '/queue/chat', function (response) {
//        // 서버에서 전송한 메시지를 수신할 때 실행되는 로직
//        var message = response.body;
//        console.log('받은 메시지: ' + message);
//
//        // TODO: 받은 메시지를 화면에 표시하거나 처리하는 로직 추가
//    });
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

//    // "/topic/messages" 토픽을 구독
//    stompClient.subscribe('/topic/messages', function (response) {
//        // 서버에서 브로드캐스트한 메시지를 수신할 때 실행되는 로직
//        var message = JSON.parse(response.body);
//        console.log('새로운 메시지 도착: ' + message);
//    });

//    // 채팅방 입장
//    var roomId = "room123"; // 채팅방 ID
//    stompClient.subscribe('/topic/messages/' + roomId, function (response) {
//        var message = JSON.parse(response.body);
//        console.log(message.sender.nickname + ': ' + message.content);
//        // 메시지를 읽었음을 서버에 전송
//        stompClient.send("/app/message-read", {}, JSON.stringify({ messageId: message.id }));
//    });
//
//    // 메시지 전송
//    var message = {
//        content: "안녕하세요!",
//        sender: { nickname: "사용자1" },
//        roomId: "room123"
//    };
//    stompClient.send("/app/chat", { roomId: roomId }, JSON.stringify(message));

    @MessageMapping("/chat") // 클라이언트에서 "/app/chat"으로 메시지를 보내면 이 메서드가 호출됨
    @SendTo("/topic/messages") // 이 메서드의 결과를 "/topic/messages" 토픽으로 브로드캐스트
    public ResponseEntity<?> handleChatMessage(@RequestHeader("access_token") String  token,
                                               @RequestBody MessageDTO message) {
        // 클라이언트로부터 받은 채팅 메시지를 처리하는 로직
        chatService.handleChatMessage(message, token);
        return ResponseEntity.status(200).body(message.getContent());
    }


}

