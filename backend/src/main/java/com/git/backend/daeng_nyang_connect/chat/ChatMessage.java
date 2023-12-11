package com.git.backend.daeng_nyang_connect.chat;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_idx")
    private Long chatMessageId;
    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    private String content;
    private LocalDateTime timestamp;

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setUser(User userFromSession) {
    }

    public void setContent(String message) {
    }

    public void setTimestamp(LocalDateTime now) {
    }
}

