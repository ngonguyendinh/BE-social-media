package com.example.mxh.service.notification;

import com.example.mxh.model.post.Post;
import com.example.mxh.model.reels.Reels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.mxh.exception.UserException;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.user.User;


import java.util.List;
import java.util.Set;

public interface INotificationService {
    Notification createPost(User user, String message, Post newPost) throws UserException;
    Notification createNotificationCreateReels(User user, String message);
    List<NotificationRecipient> getNotificationsForUser(int userId);
    NotificationRecipient readNotification(Long id, Long recipientId);
    Notification createNotificationLikePost( User postOwner,User liker, String message);
    Notification createNotificationCommentPost(User postOwner, User commenter, String message);
    Page<NotificationRecipient> getPagedNotificationsForUser(Long userId, Pageable pageable);
    Long countUnreadNotifications(int userId);
    void markAllNotificationsAsRead(int userId);
    Notification createNotification(Notification notification);
    void deleteNotification(Long id);
    Notification createNotificationFollowUser(User user, User follower, String message);
    List<NotificationRecipient> filterNotifications(int userId, String type, Boolean read);
}
