package com.example.mxh.map;

import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationRecipientDto;
import com.example.mxh.model.story.Story;
import com.example.mxh.model.story.StoryDto;

import java.util.ArrayList;
import java.util.List;

public class NotificationRecipientMapper {
    public static NotificationRecipientDto map(NotificationRecipient notificationRecipient){
        NotificationRecipientDto dto = new NotificationRecipientDto();
        dto.setId(notificationRecipient.getId());
        dto.setIsRead(notificationRecipient.getIsRead());
        dto.setNotification(NotificationMapper.map(notificationRecipient.getNotification()));
        dto.setRecipient(UserMapper.map(notificationRecipient.getRecipient()));
        dto.setReceivedAt(notificationRecipient.getReceivedAt());
        dto.setIsRead(notificationRecipient.getIsRead());
        return dto;
    }
    public static List<NotificationRecipientDto> map(List<NotificationRecipient> notificationRecipients){
        List<NotificationRecipientDto> notificationRecipientDtos = new ArrayList<>();
        for (NotificationRecipient notificationRecipient : notificationRecipients) {
            NotificationRecipientDto storyDto = map(notificationRecipient);
            notificationRecipientDtos.add(storyDto);
        }
        return notificationRecipientDtos;
    }
}
