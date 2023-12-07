package com.git.backend.daeng_nyang_connect.chat;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_ids")
    private Long chatRoomId;

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(mappedBy = "chatRoom")
    private Set<ChatMessage> messages;

    @OneToMany(mappedBy = "chatRoom")
    private Set<ChatRoomSession> sessions;
}
