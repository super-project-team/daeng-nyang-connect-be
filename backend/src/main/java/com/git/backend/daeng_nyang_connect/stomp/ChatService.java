package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.animal.repository.AnimalRepository;
import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Cacheable
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TokenProvider tokenProvider;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MessageRepository messageRepository;
    private final AnimalRepository animalRepository;


    public void addChatRoom(String token, Long animalId) {
        String email = tokenProvider.getEmailBytoken(token);
        User sender = userRepository.findByEmail(email).orElseThrow(
                ()->new NoSuchElementException("토큰을 확인해주세요.")
        );
        Animal board = animalRepository.findById(animalId).orElseThrow(
                ()->new NoSuchElementException("없는 게시글입니다.")
        );


        // 예시로 chatRoom을 생성하는 코드
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomName("채팅방")
                .build();

        chatRoomRepository.save(newChatRoom);

        addUser(newChatRoom, sender);
        addUser(newChatRoom, board.getUser());
    }

    public void addUser(ChatRoom chatRoom, User user){
        ChatRoomUser addUser = ChatRoomUser.builder().chatRoom(chatRoom).user(user).build();
        chatRoomUserRepository.save(addUser);
    }

    public MessageDTO handleChatMessage(MessageDTO message, String token) {
        String email = tokenProvider.getEmailBytoken(token);
        User sender = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("없는 유저입니다")
        );

        message.setSender(sender.getNickname());

        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId()).orElseThrow(
                () -> new NoSuchElementException("없는 채팅방입니다")
        );

        if (!chatRoom.getUserList().contains(chatRoomUserRepository.findByUser(sender))) {
            throw new NoSuchElementException("해당 채팅방에 당신은 없습니다");
        }

        return message;
    }

    public void saveMessage(MessageDTO message){
        User sender = userRepository.findByNickname(message.getSender());
        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId()).orElseThrow(
                ()->new NoSuchElementException("없는 채팅방입니다.")
        );
        Message newMessage = Message.builder()
                .sender(sender)
                .content(message.getContent())
                .chatRoom(chatRoom)
                .build();

        messageRepository.save(newMessage);
    }
}

