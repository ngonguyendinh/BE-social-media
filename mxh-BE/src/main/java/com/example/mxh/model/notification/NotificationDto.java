package com.example.mxh.model.notification;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationDto {
    private Long id;
    private String notification;
    private Boolean isRead;
    private LocalDateTime receivedAt;
    private List<Long> recipientIds;
}
