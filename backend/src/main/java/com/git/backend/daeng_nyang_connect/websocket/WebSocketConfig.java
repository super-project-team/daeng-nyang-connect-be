package com.git.backend.daeng_nyang_connect.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //STOMP 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  //엔드포인트
                .setAllowedOrigins("*")
                .withSockJS();    //cors 오류 방지
    }

    // Message broker 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ///sub가 prefix로 붙은 destination의 클라이언트에게 메시지를 보낼 수 있도록 Simple Broker를 등록
        registry.enableSimpleBroker("/sub");

        ///pub가 prefix로 붙은 메시지들은 @MessageMapping이 붙은 method로 바운드
        registry.setApplicationDestinationPrefixes("/pub");
    }

    // STOMP 연결 시도 시 호출되는 메소드
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //인터셉터를 등록해서 연결을 시도하면 FilterChannelInterceptor가 실행되게 설정
        registration.interceptors(new FilterChannelInterceptor());
    }
}
