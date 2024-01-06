package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Cacheable
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public List<ChatRoom> myRoom(@RequestHeader("access_token") String token) {
        String email = tokenProvider.getEmailBytoken(token);
        User user = userRepository.findByEmail(email).orElseThrow();
        return chatRoomUserRepository.findMyChatRoom(user.getUserId());
    }
}
