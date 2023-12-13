//package com.git.backend.daeng_nyang_connect.chat;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "chat_room_sessions")
//public class ChatRoomSession {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chat_room_session_idx")
//    private Long chatRoomSessionId;
//
//    @ManyToOne
//    @JoinColumn(name = "chat_room_idx")
//    private ChatRoom chatRoom;
//
//    @Column(name = "web_socket_session_id")
//    private String webSocketSessionId;
//
//
//
//    public void setChatRoom(ChatRoom chatRoom) {
//        this.chatRoom = chatRoom;
//    }
//
//    public void setWebSocketSessionId(String id) {
//        this.webSocketSessionId = id;
//    }
//
//    // 다른 필드들...
//
//    // 생성자, 게터, 세터 등 필요한 메서드들...
//}
