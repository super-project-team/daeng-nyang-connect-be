package com.git.backend.daeng_nyang_connect.chat;

import lombok.*;


//** 웹소켓 통신 시 보내는 양식
//
//        {
//        "messageType":"TALK", // ENTER, TALK
//        "chatRoomId":1, // 채팅방 번호
//        "senderId":100, // 메세지 전송자의 UserId
//        "message":"hello" // 메세지 내용
//        }
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방 번호
    private Long senderId; // 채팅을 보낸 사람
    private String message; // 메시지
}
