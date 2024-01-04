package com.git.backend.daeng_nyang_connect.websocket;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;


@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class FilterChannelInterceptor implements ChannelInterceptor {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    // 메시지가 채널로 전송되기 전에 호출되는 메소드
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert headerAccessor != null;
        if (headerAccessor.getCommand() == StompCommand.CONNECT) { // 연결 시에한 header 확인
            String token = String.valueOf(headerAccessor.getNativeHeader("Authorization").get(0));

            try {
                String userEmail = tokenProvider.getEmailBytoken(token);
                User user = userRepository.findByEmail(userEmail).orElseThrow(
                        ()-> new NullPointerException(" ")
                );
                Long userId = user.getUserId();

                headerAccessor.addNativeHeader("User", String.valueOf(userId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}