package com.git.backend.daeng_nyang_connect.notify.controller;

import com.git.backend.daeng_nyang_connect.notify.dto.NotificationDTO;
import com.git.backend.daeng_nyang_connect.notify.service.NotificationService;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Cacheable
@RestController
@RequiredArgsConstructor
@Tag(name = "실시간 알림")
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 가져오기")
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@RequestHeader("access_token") String token) {
        User user = notificationService.getUserFromToken(token);
        if (user != null) {
            List<NotificationDTO> unreadNotifications = notificationService.getUnreadNotificationsDTO(token);
            return ResponseEntity.ok(unreadNotifications);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "실시간 알림 / SSE 에미터 생성")
    @GetMapping("/sse-emitter")
    public ResponseEntity<SseEmitter> createSseEmitter(@RequestHeader("access_token")  String token) {
        SseEmitter sseEmitter = notificationService.createSseEmitter(token);
        return ResponseEntity.ok(sseEmitter);
    }

    @Operation(summary = "SSE 에미터 제거")
    @DeleteMapping("/sse-emitter")
    public ResponseEntity<Void> removeSseEmitter(@RequestHeader("access_token") String token) {
        notificationService.removeEmitter(token);
        return ResponseEntity.ok().build();
    }
}
