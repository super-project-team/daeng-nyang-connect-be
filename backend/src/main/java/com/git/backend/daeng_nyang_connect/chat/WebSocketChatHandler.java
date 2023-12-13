//package com.git.backend.daeng_nyang_connect.chat;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.git.backend.daeng_nyang_connect.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.*;
//
///*
// * WebSocket Handler 작성
// * 소켓 통신은 서버와 클라이언트가 1:n으로 관계를 맺는다. 따라서 한 서버에 여러 클라이언트 접속 가능
// * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 핸들러가 필요
// * TextWebSocketHandler를 상속받아 핸들러 작성
// * 클라이언트로 받은 메세지를 log로 출력하고 클라이언트로 환영 메세지를 보내줌
// * */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketChatHandler extends TextWebSocketHandler {
//    private final ObjectMapper mapper;
//    private final ChatRoomRepository chatRoomRepository;
//    private final ChatRoomSessionRepository chatRoomSessionRepository;
//    private final ChatMessageRepository chatMessageRepository;
//
//    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("{} 연결됨", session.getId());
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        ChatMessageDto chatMessage = mapper.readValue(payload, ChatMessageDto.class);
//        log.info("session {}", chatMessage.toString());
//
//        Long chatRoomId = chatMessage.getChatRoomId();
//
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomId);
//        if (optionalChatRoom.isPresent()) {
//            ChatRoom chatRoom = optionalChatRoom.get();
//            //computeIfAbsent : 해당 키가 맵에 존재하지 않으면 새로운 값을 생성하고 추가하고, 이미 존재하면 기존 값을 반환
//            Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.computeIfAbsent(chatRoomId, k -> new HashSet<>());
//
//            if (chatMessage.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
//                chatRoomSession.add(session);
//
//                ChatRoomSession chatRoomSessionEntity = new ChatRoomSession();
//                chatRoomSessionEntity.setChatRoom(chatRoom);
//                chatRoomSessionEntity.setWebSocketSessionId(session.getId());
//                chatRoomSessionRepository.save(chatRoomSessionEntity);
//            }
//
//            if (chatRoomSession.size() >= 3) {
//                removeClosedSession(chatRoomSession);
//            }
//
//            // Save the chat message to the database
//            saveChatMessage(chatMessage, session, chatRoom);
//
//            sendMessageToChatRoom(chatMessage, chatRoomSession);
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info("{} 연결 끊김", session.getId());
//        chatRoomSessionRepository.deleteByWebSocketSessionId(session.getId());
//        chatRoomSessionMap.values().forEach(sessions -> sessions.remove(session));
//    }
//
//    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.removeIf(sess -> !sess.isOpen());
//    }
//
//    private void sendMessageToChatRoom(ChatMessageDto chatMessage, Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessage));
//    }
//
//    private void sendMessage(WebSocketSession session, ChatMessageDto chatMessage) {
//        try {
//            session.sendMessage(new TextMessage(mapper.writeValueAsString(chatMessage)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    private void saveChatMessage(ChatMessageDto chatMessage, WebSocketSession session, ChatRoom chatRoom) {
//        // Save the chat message to the database
//        ChatMessage chatMessageEntity = new ChatMessage();
//        chatMessageEntity.setChatRoom(chatRoom);
//        chatMessageEntity.setUser(getUserFromSession(session)); // Implement this method to get the user from session
//        chatMessageEntity.setContent(chatMessage.getMessage());
//        chatMessageEntity.setTimestamp(LocalDateTime.now());
//        chatMessageRepository.save(chatMessageEntity);
//    }
//
//    // Implement this method to get the user from the session
//    private User getUserFromSession(WebSocketSession session) {
//        // Implement the logic to get the user from the session
//        return null;
//    }
//}
//
//
