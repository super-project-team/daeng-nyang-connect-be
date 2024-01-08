package com.git.backend.daeng_nyang_connect.notify.controller;

import com.git.backend.daeng_nyang_connect.notify.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Tag(name = "실시간 알림")
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "댓글 & 좋아요 알림")
    @GetMapping("")
    public SseEmitter getNotificationEmitter(@RequestHeader("access_token") String token) {
        return notificationService.createSseEmitter(token);
    }

    @DeleteMapping("/delete")
    public void removeNotificationEmitter(@RequestHeader("access_token") String token) {
        notificationService.removeEmitter(token);
    }
}
