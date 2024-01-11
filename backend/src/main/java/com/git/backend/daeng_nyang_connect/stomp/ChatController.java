package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public Set<ChatRoomResponseDTO> myRoom(@RequestHeader("access_token") String token) {
//        내가 속한 채팅방 리스트 - user
//        그 채팅방의 유저 뽑기
        String email = tokenProvider.getEmailBytoken(token);
        User user = userRepository.findByEmail(email).orElseThrow();
        List<ChatRoom> myRoomList= chatRoomUserRepository.findMyChatRoom(user.getUserId());
        Set<ChatRoomResponseDTO> chatRoomSet = new HashSet<>();
        for(ChatRoom chatRoom:myRoomList){
            ChatRoomResponseDTO chatRoomInfo = new ChatRoomResponseDTO(chatRoom);
            chatRoomSet.add(chatRoomInfo);
        }
        return chatRoomSet;
    }

    @GetMapping("/room/detail")
    public ChatRoomInfo myRoomDetail(@RequestHeader("access_token") String token,
                                     @RequestParam("chatRoomId") Long chatRoomId) {
        String email = tokenProvider.getEmailBytoken(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new NoSuchElementException("없는 유저")
        );
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
                ()->new NoSuchElementException("없는 채팅방")
        );
        ChatRoomInfo chatRoomInfo = new ChatRoomInfo(chatRoom);
        return chatRoomInfo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@RequestHeader("access_token") String token,
                                     @RequestParam("animalId") Long animalId){
        chatService.addChatRoom(token, animalId);
        return ResponseEntity.status(200).body("채팅방 생성 완료");
    }
}
