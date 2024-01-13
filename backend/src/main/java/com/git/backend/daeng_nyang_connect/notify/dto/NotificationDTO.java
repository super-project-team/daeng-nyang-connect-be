package com.git.backend.daeng_nyang_connect.notify.dto;

import com.git.backend.daeng_nyang_connect.notify.entity.Notification;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {
    private String eventName;
    private String eventData;
    private LocalDateTime timestamp;
    private boolean isRead;

    public static NotificationDTO fromEntity(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEventName(notification.getEventName());
        notificationDTO.setEventData(notification.getEventData());
        notificationDTO.setTimestamp(notification.getTimestamp());
        notificationDTO.setRead(notification.isRead());
        return notificationDTO;
    }
}
