package com.example.mxh.model.notification;


import com.example.mxh.model.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class NotificationRecipientDto {

    private Long id;


    private NotificationDto notification;


    private UserDto recipient;

    private Boolean isRead = false;

    private LocalDateTime receivedAt;
}
