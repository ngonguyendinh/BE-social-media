package com.example.mxh.model.notification;


import com.example.mxh.model.post.PostDto;
import com.example.mxh.model.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationDto {
    private Long id;
    private String notification;
    private LocalDateTime receivedAt;
    private PostDto newPost;
    private UserDto sender;
    private List<Long> recipientIds;
}
