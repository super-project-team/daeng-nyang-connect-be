package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Cacheable
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final TokenProvider tokenProvider;
    private final ChatRoomUserRepository chatRoomUserRepository;

    public void handleChatMessage(MessageDTO message, String token) {
        String email = tokenProvider.getEmailBytoken(token);
        User sender = userRepository.findByEmail(email).orElseThrow();
        if(chatRoomRepository.findById(message.getRoomId()).isEmpty()){
            addChatRoom(token, message.getReceiverUserId());
        }
        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId()).orElseThrow();

        if (!chatRoom.getUserList().contains(chatRoomUserRepository.findByUser(sender))) {
            throw new NoSuchElementException("x");
        }

        Message sendMessage = Message.builder()
                                    .content(message.getContent())
                                    .chatRoom(chatRoom)
                                    .sender(sender)
                                    .build();

        // 메시지 저장
        messageRepository.save(sendMessage);

        message.setSender(sender.getNickname());

        // 채팅방 내 모든 사용자에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoom.getChatRoomId(), message);
    }

    public void addChatRoom(String token, Long receiverUserId) {

        String email = tokenProvider.getEmailBytoken(token);
        User receiverUser = userRepository.findById(receiverUserId).orElseThrow();
        User sender = userRepository.findByEmail(email).orElseThrow();
        // 예시로 chatRoom을 생성하는 코드
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomName("채팅방")
                .build();

        chatRoomRepository.save(newChatRoom);

        addUser(newChatRoom, sender);
        addUser(newChatRoom, receiverUser);
    }

    public void addUser(ChatRoom chatRoom, User user){
        ChatRoomUser addUser = ChatRoomUser.builder().chatRoom(chatRoom).user(user).build();
        chatRoomUserRepository.save(addUser);
    }
}

