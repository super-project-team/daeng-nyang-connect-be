package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_idx")
    private Long messageId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;

    // Getter, Setter, 생성자 등 필요한 메서드 구현
}
