//package com.example.mxh.service.notification;
//
//import com.example.mxh.exception.UserException;
//import com.example.mxh.model.notification.Notification;
//import com.example.mxh.model.notification.NotificationRecipient;
//import com.example.mxh.model.notification.NotificationTask;
//import com.example.mxh.model.user.User;
//import com.example.mxh.repository.NotificationRecipientRepository;
//import com.example.mxh.repository.NotificationRepository;
//import com.example.mxh.service.user.IUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//
//@Service
//@AllArgsConstructor
//public class NotificationService implements  INotificationService{
//
//    private NotificationRepository notificationRepository;
//    private NotificationRecipientRepository notificationRecipientRepository;
//    private NotificationWorkQueu notificationWorkQueu;
//
//    private IUserService userService;
//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//    @Override
//    public Notification createPost( User user , String message) throws UserException {
//        Notification notification = new Notification();
//        notification.setMessage(message);
//        notification.setUser(user);
//        notification.setType("newPost");
//        notificationRepository.save(notification);
//        // Lấy danh sách người theo dõi
//        Set<User> followers = new HashSet<>();
//        if (user.getFollowing() != null && !user.getFollowing().isEmpty()) {
//            followers = userService.findUsersByIds(user.getFollowing());
//        }
//        if (followers == null || followers.isEmpty()) {
//            return notificationRepository.save(notification);
//        }
//        for(User follower : followers){
//            NotificationRecipient recipient = new NotificationRecipient();
//            recipient.setNotification(notification);
//            recipient.setRecipient(follower);
//            recipient.setIsRead(false);
//            recipient.setReceivedAt(LocalDateTime.now());
//            NotificationRecipient savedRecipient = notificationRecipientRepository.save(recipient);
//
//            // Tạo nhiệm vụ gửi thông báo
//            NotificationTask task = new NotificationTask(
//                    savedRecipient.getId(),
//                    Long.valueOf( follower.getId()),
//                    message
//            );
//            notificationWorkQueu.enqueue(task);
//        }
//        return notificationRepository.save(notification);
//    }
//
//    @Override
//    public List<NotificationRecipient> getNotificationsForUser(int userId) {
//        return notificationRecipientRepository.findByRecipientIdAndIsReadFalse(userId);
//    }
//
//    @Override
//    public NotificationRecipient readNotification(Long id) {
//        NotificationRecipient notificationRecipient = notificationRecipientRepository.findById(id).get();
//        notificationRecipient.setIsRead(true);
//        return notificationRecipientRepository.save(notificationRecipient);
//    }
//
//    @Override
//    public Notification createNotificationLikePost( User postOwner, User liker,String message) {
//        Notification notification = new Notification();
//        notification.setMessage(message);
//        notification.setUser(liker);
//        notification.setType("like");
//        notificationRepository.save(notification);
//
//        // Tạo thông báo cho chủ bài viết
//        NotificationRecipient recipient = new NotificationRecipient();
//        recipient.setNotification(notification);
//        recipient.setRecipient(postOwner);
//        recipient.setIsRead(false);
//        recipient.setReceivedAt(LocalDateTime.now());
//        NotificationRecipient savedRecipient = notificationRecipientRepository.save(recipient);
//
//        // Tạo nhiệm vụ gửi thông báo
//        NotificationTask task = new NotificationTask(
//                savedRecipient.getId(),
//                Long.valueOf(postOwner.getId()),
//                message
//        );
//        notificationWorkQueu.enqueue(task);
//
//        return notification;
//    }
//
//
//}
package com.example.mxh.service.notification;

import com.example.mxh.exception.UserException;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationTask;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.NotificationRecipientRepository;
import com.example.mxh.repository.NotificationRepository;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {

    private NotificationRepository notificationRepository;
    private NotificationRecipientRepository notificationRecipientRepository;
    private NotificationWorkQueu notificationWorkQueu;
    private IUserService userService;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public Notification createPost(User user, String message) throws UserException {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setType("newPost");
        notificationRepository.save(notification);
        // Lấy danh sách người theo dõi
        Set<User> followers = new HashSet<>();
        if (user.getFollower() != null && !user.getFollower().isEmpty()) {
            followers = userService.findUsersByIds(user.getFollower());
        }
        if (followers == null || followers.isEmpty()) {
            return notificationRepository.save(notification);
        }
        for (User follower : followers) {
            NotificationRecipient recipient = new NotificationRecipient();
            recipient.setNotification(notification);
            recipient.setRecipient(follower);
            recipient.setIsRead(false);
            recipient.setReceivedAt(LocalDateTime.now());
            NotificationRecipient savedRecipient = notificationRecipientRepository.save(recipient);

            // Tạo nhiệm vụ gửi thông báo
            NotificationTask task = new NotificationTask(
                    savedRecipient.getId(),
                    Long.valueOf(follower.getId()),
                    message
            );
            notificationWorkQueu.enqueue(task);
        }
        return notificationRepository.save(notification);
    }

    @Override
    public List<NotificationRecipient> getNotificationsForUser(int userId) {
        return notificationRecipientRepository.findByRecipientIdAndIsReadFalse(userId);
    }

    @Override
    public NotificationRecipient readNotification(Long id) {
        NotificationRecipient notificationRecipient = notificationRecipientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        notificationRecipient.setIsRead(true);
        return notificationRecipientRepository.save(notificationRecipient);
    }

    @Override
    public Notification createNotificationLikePost(User postOwner, User liker, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(liker);
        notification.setType("like");
        notificationRepository.save(notification);

        // Tạo thông báo cho chủ bài viết
        NotificationRecipient recipient = new NotificationRecipient();
        recipient.setNotification(notification);
        recipient.setRecipient(postOwner);
        recipient.setIsRead(false);
        recipient.setReceivedAt(LocalDateTime.now());
        NotificationRecipient savedRecipient = notificationRecipientRepository.save(recipient);

        // Tạo nhiệm vụ gửi thông báo
        NotificationTask task = new NotificationTask(
                savedRecipient.getId(),
                Long.valueOf(postOwner.getId()),
                message
        );
        notificationWorkQueu.enqueue(task);

        return notification;
    }

    @Override
    public Page<NotificationRecipient> getPagedNotificationsForUser(Long userId, Pageable pageable) {
        return notificationRecipientRepository.findByRecipientId(userId, pageable);
    }

    @Override
    public Long countUnreadNotifications(int userId) {
        return notificationRecipientRepository.countByRecipientIdAndIsReadFalse(userId);
    }

    @Override
    @Transactional
    public void markAllNotificationsAsRead(int userId) {
        notificationRecipientRepository.markAllAsReadForUser(userId);
    }

    @Override
    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        // Xóa tất cả các recipient trước
        notificationRecipientRepository.deleteByNotificationId(id);
        // Sau đó xóa notification
        notificationRepository.deleteById(id);
    }

    @Override
    public List<NotificationRecipient> filterNotifications(int userId, String type, Boolean read) {
        if (type != null && read != null) {
            return notificationRecipientRepository.findByRecipientIdAndNotificationTypeAndIsRead(userId, type, read);
        } else if (type != null) {
            return notificationRecipientRepository.findByRecipientIdAndNotificationType(userId, type);
        } else if (read != null) {
            return notificationRecipientRepository.findByRecipientIdAndIsRead(userId, read);
        } else {
            return notificationRecipientRepository.findByRecipientId((long) userId);
        }
    }
}
