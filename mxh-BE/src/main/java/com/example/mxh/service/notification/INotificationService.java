package com.example.mxh.service.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.mxh.exception.UserException;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.user.User;


import java.util.List;
import java.util.Set;

public interface INotificationService {
    Notification createPost(User user, String message) throws UserException;
    List<NotificationRecipient> getNotificationsForUser(int userId);
    NotificationRecipient readNotification(Long id);
    Notification createNotificationLikePost( User postOwner,User liker, String message);
    Page<NotificationRecipient> getPagedNotificationsForUser(Long userId, Pageable pageable);
    Long countUnreadNotifications(int userId);
    void markAllNotificationsAsRead(int userId);
    Notification createNotification(Notification notification);
    void deleteNotification(Long id);
    List<NotificationRecipient> filterNotifications(int userId, String type, Boolean read);
}
