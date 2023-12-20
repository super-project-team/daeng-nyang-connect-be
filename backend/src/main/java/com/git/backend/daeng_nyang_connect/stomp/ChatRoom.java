package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_idx")
    private Long chatRoomId;

    private String roomName;

    @OneToMany(mappedBy = "chatRoom")
    private Set<ChatRoomUser> userList = new HashSet<>();
    // Getter, Setter, 생성자 등 필요한 메서드 구현
}

