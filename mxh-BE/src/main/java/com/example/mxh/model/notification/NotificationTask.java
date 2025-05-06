package com.example.mxh.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor

public class NotificationTask {
    private final Long notificationRecipientId;
    private final Long recipientId;
    private final String messageContent;
}
