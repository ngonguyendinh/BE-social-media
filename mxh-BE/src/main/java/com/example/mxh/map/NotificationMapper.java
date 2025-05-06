package com.example.mxh.map;

import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationDto;
import com.example.mxh.model.notification.NotificationRecipient;

public class NotificationMapper {
    public static NotificationDto map(NotificationRecipient notificationRecipient){
        NotificationDto dto = new NotificationDto();

        dto.setNotification(notificationRecipient.getNotification().getMessage());
        dto.setReceivedAt(notificationRecipient.getReceivedAt());
        return dto;
    }
    public static NotificationDto map(Notification notification){
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setNotification(notification.getMessage());
        dto.setReceivedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification map(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setMessage(notificationDto.getNotification());
        return notification;
    }
}