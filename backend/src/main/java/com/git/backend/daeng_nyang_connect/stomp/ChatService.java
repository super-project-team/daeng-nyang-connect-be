package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final TokenProvider tokenProvider;

    public void handleChatMessage(Message message) {
        //토큰을 받아 해당 유저도 같이 확인
        User sender = userRepository.findByNickname(message.getSender().getNickname());
        ChatRoom chatRoom = chatRoomRepository.findByRoomName(message.getRoomName());

        if (sender == null || chatRoom == null || !chatRoom.getUsers().contains(sender)) {
            throw new NoSuchElementException("x");
        }
        message.setSender(sender);

        // 메시지 저장
        messageRepository.save(message);

        // 채팅방 내 모든 사용자에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages/" + message.getRoomName(), message);
    }

    public void sendChatRequest(String receiverUsername, String senderName) {
        // "/user/{receiverUsername}/queue/chat" 큐로 메시지를 전송
        messagingTemplate.convertAndSendToUser(receiverUsername, "/queue/chat", senderName);
    }

    public void addChatRoom(String receiverUsername, String token) {

        String email = tokenProvider.getEmailBytoken(token);
        Set<User> users = new HashSet<>();
        User receiverUser = userRepository.findByNickname(receiverUsername);
        User sender = userRepository.findByEmail(email).orElseThrow();
        users.add(receiverUser);
        users.add(sender);
        // 예시로 chatRoom을 생성하는 코드
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomName("채팅방")
                .users(users)
                .build();

        chatRoomRepository.save(newChatRoom);

        // 상대방에게 chatRoom 생성을 알리는 메시지 전송
        messagingTemplate.convertAndSendToUser(receiverUsername, "/queue/chat", "채팅을 시작합니다.");
    }
}

