////package com.example.mxh.service.notification;
////
////import com.example.mxh.model.notification.NotificationTask;
////import lombok.AllArgsConstructor;
////import lombok.RequiredArgsConstructor;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.messaging.simp.SimpMessagingTemplate;
////import org.springframework.stereotype.Service;
////
////import java.util.concurrent.BlockingQueue;
////import java.util.concurrent.ExecutorService;
////import java.util.concurrent.Executors;
////import java.util.concurrent.LinkedBlockingQueue;
////@Service
////
////public class NotificationWorkQueu {
////    private final BlockingQueue<NotificationTask> taskQueue = new LinkedBlockingQueue<>();
////
////    private final SimpMessagingTemplate messagingTemplate;
////    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
////    @Autowired
////    public NotificationWorkQueu(SimpMessagingTemplate messagingTemplate) {
////        this.messagingTemplate = messagingTemplate;
////        // Khởi chạy các worker
////        for (int i = 0; i < 5; i++) {
////            executorService.submit(this::processQueue);
////        }
////    }
////    public void enqueue(NotificationTask task) {
////        taskQueue.offer(task);
////    }
////
////
////    private void processQueue() {
////        while (true) {
////            try {
////                NotificationTask task = taskQueue.take();
////                messagingTemplate.convertAndSend("/topic/notifications/" + task.getRecipientId(),
////                        task.getMessageContent());
////
////            } catch (InterruptedException e) {
////                Thread.currentThread().interrupt();
////                break;
////            }
////        }
////    }
////
////}
//package com.example.mxh.service.notification;
//
//import com.example.mxh.map.NotificationMapper;
//import com.example.mxh.map.NotificationRecipientMapper;
//import com.example.mxh.model.notification.*;
//import com.example.mxh.repository.NotificationRecipientRepository;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//
//@Service
//@Slf4j
//public class NotificationWorkQueu {
//    private final BlockingQueue<NotificationTask> taskQueue = new LinkedBlockingQueue<>();
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ExecutorService executorService;
//    private final NotificationRecipientRepository notificationRecipientRepository;
//
//    @Autowired
//    public NotificationWorkQueu(
//            SimpMessagingTemplate messagingTemplate,
//            NotificationRecipientRepository notificationRecipientRepository) {
//        this.messagingTemplate = messagingTemplate;
//        this.notificationRecipientRepository = notificationRecipientRepository;
//        this.executorService = Executors.newFixedThreadPool(5);
//
//        // Khởi chạy các worker
//        for (int i = 0; i < 5; i++) {
//            executorService.submit(this::processQueue);
//        }
//
//        log.info("Notification work queue initialized with 5 workers");
//    }
//
//    /**
//     * Thêm một nhiệm vụ thông báo vào hàng đợi
//     */
//    public void enqueue(NotificationTask task) {
//        taskQueue.offer(task);
//        log.debug("Enqueued notification task for recipient: {}", task.getRecipientId());
//    }
//
//    /**
//     * Xử lý các nhiệm vụ thông báo từ hàng đợi
//     */
////    @Transactional
////    private void processQueue() {
////        while (true) {
////            try {
////                NotificationTask task = taskQueue.take();
////                log.debug("Processing notification task for recipient: {}", task.getRecipientId());
////
////                try {
////                    // Lấy thông tin đầy đủ về thông báo từ database
////                    Optional<NotificationRecipient> recipientOpt =
////                            notificationRecipientRepository.findById(task.getNotificationRecipientId());
////
////                    if (recipientOpt.isPresent()) {
////                        NotificationRecipient recipient = recipientOpt.get();
////                        Notification notification = recipient.getNotification();
////
////                        // Tạo DTO để gửi qua WebSocket
////                        NotificationDto notificationDto = new NotificationDto();
////                        notificationDto.setId(notification.getId());
////                        notificationDto.setNotification(notification.getMessage());
////                        notificationDto.setReceivedAt(recipient.getReceivedAt());
////
////                        // Gửi thông báo qua cả hai kênh để đảm bảo tương thích
////                        // 1. Gửi qua kênh /user/{userId}/notifications (cách mới)
////                        messagingTemplate.convertAndSendToUser(
////                                task.getRecipientId().toString(),
////                                "/notifications",
////                                notificationDto
////                        );
////
////                        // 2. Gửi qua kênh /topic/notifications/{userId} (cách cũ)
////                        messagingTemplate.convertAndSend(
////                                "/topic/notifications/" + task.getRecipientId(),
////                                notificationDto
////                        );
////
////                        log.debug("Notification sent to recipient: {}", task.getRecipientId());
////                    } else {
////                        log.warn("NotificationRecipient not found with ID: {}", task.getNotificationRecipientId());
////                    }
////                } catch (Exception e) {
////                    log.error("Error processing notification task: {}", e.getMessage(), e);
////                }
////            } catch (InterruptedException e) {
////                log.warn("Notification worker interrupted", e);
////                Thread.currentThread().interrupt();
////                break;
////            }
////        }
////    }
//
//    @Transactional
//    private void processQueue() {
//        while (true) {
//            try {
//                NotificationTask task = taskQueue.take();
//                log.debug("Processing notification task for recipient: {}", task.getRecipientId());
//
//                try {
//                    // Lấy thông tin đầy đủ về thông báo từ database
//                    Optional<NotificationRecipient> recipientOpt =
//                            notificationRecipientRepository.findById(task.getNotificationRecipientId());
//
//                    if (recipientOpt.isPresent()) {
//                        NotificationRecipient recipient = recipientOpt.get();
//
//                        // Tạo NotificationRecipientDto để gửi qua WebSocket
//                        NotificationRecipientDto recipientDto = NotificationRecipientMapper.map(recipient);
////
////                        // Gửi thông báo qua cả hai kênh để đảm bảo tương thích
////                        // 1. Gửi qua kênh /user/{userId}/notifications (cách mới)
////                        messagingTemplate.convertAndSendToUser(
////                                task.getRecipientId().toString(),
////                                "/notifications",
////                                recipientDto
////                        );
////
////                        // 2. Gửi qua kênh /topic/notifications/{userId} (cách cũ)
////                        messagingTemplate.convertAndSend(
////                                "/topic/notifications/" + task.getRecipientId(),
////                                recipientDto
////                        );
//                        // Gửi thông báo đến follower không phải người thực hiện follow
//                        messagingTemplate.convertAndSendToUser(
//                                recipient.getRecipient().getUsername(),
//                                "/notifications",
//                                recipientDto
//                        );
//
//
//                        log.debug("Notification sent to recipient: {}", task.getRecipientId());
//                    } else {
//                        log.warn("NotificationRecipient not found with ID: {}", task.getNotificationRecipientId());
//                    }
//                } catch (Exception e) {
//                    log.error("Error processing notification task: {}", e.getMessage(), e);
//                }
//            } catch (InterruptedException e) {
//                log.warn("Notification worker interrupted", e);
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    }
//
//    /**
//     * Đóng hàng đợi và giải phóng tài nguyên
//     */
//    public void shutdown() {
//        executorService.shutdownNow();
//        log.info("Notification work queue shutdown");
//    }
//}
package com.example.mxh.service.notification;

import com.example.mxh.map.NotificationRecipientMapper;
import com.example.mxh.model.notification.*;
import com.example.mxh.repository.NotificationRecipientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class NotificationWorkQueu {
    private final BlockingQueue<NotificationTask> taskQueue = new LinkedBlockingQueue<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRecipientRepository notificationRecipientRepository;
    private final ExecutorService executorService;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public NotificationWorkQueu(
            SimpMessagingTemplate messagingTemplate,
            NotificationRecipientRepository notificationRecipientRepository,
            PlatformTransactionManager transactionManager) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRecipientRepository = notificationRecipientRepository;
        this.executorService = Executors.newFixedThreadPool(5);
        this.transactionTemplate = new TransactionTemplate(transactionManager);

        // Khởi chạy các worker
        for (int i = 0; i < 5; i++) {
            executorService.submit(this::processQueue);
        }

        log.info("Notification work queue initialized with 5 workers");
    }

    /**
     * Thêm một nhiệm vụ thông báo vào hàng đợi
     */
    public void enqueue(NotificationTask task) {
        taskQueue.offer(task);
        log.debug("Enqueued notification task for recipient: {}", task.getRecipientId());
    }

    /**
     * Xử lý các nhiệm vụ thông báo từ hàng đợi
     */
    private void processQueue() {
        while (true) {
            try {
                NotificationTask task = taskQueue.take();
                log.info("Processing notification task for recipient: {}", task.getRecipientId());

                // Sử dụng TransactionTemplate để tạo giao dịch mới
                transactionTemplate.execute(status -> {
                    try {
                        // Sử dụng truy vấn JOIN FETCH để tránh LazyInitializationException
                        Optional<NotificationRecipient> recipientOpt =
                                notificationRecipientRepository.findById(task.getNotificationRecipientId());

                        if (recipientOpt.isPresent()) {
                            NotificationRecipient recipient = recipientOpt.get();

                            // Khởi tạo các thuộc tính lazy-loaded
                            Hibernate.initialize(recipient.getNotification());
                            Hibernate.initialize(recipient.getRecipient());

                            // Lấy username và userId
                            String username = recipient.getRecipient().getUsername();
                            Long userId = Long.valueOf(recipient.getRecipient().getId());

                            // Tạo DTO để gửi qua WebSocket
                            NotificationRecipientDto recipientDto = NotificationRecipientMapper.map(recipient);

                            log.info("Sending notification to username: {}, userId: {}", username, userId);

                            // Gửi thông báo theo nhiều cách để đảm bảo client nhận được

                            // Cách 1: Gửi theo username - client subscribe '/user/notifications'
                            if (username != null && !username.isEmpty()) {
                                try {
                                    messagingTemplate.convertAndSendToUser(
                                            username,
                                            "/notifications",
                                            recipientDto
                                    );
                                    log.info("Sent notification to /user/{}/notifications", username);
                                } catch (Exception e) {
                                    log.error("Error sending to /user/{}/notifications: {}", username, e.getMessage());
                                }
                            }

                            // Cách 2: Gửi theo topic - client subscribe '/topic/notifications/{userId}'
                            try {
                                messagingTemplate.convertAndSend(
                                        "/topic/notifications/" + userId,
                                        recipientDto
                                );
                                log.info("Sent notification to /topic/notifications/{}", userId);
                            } catch (Exception e) {
                                log.error("Error sending to /topic/notifications/{}: {}", userId, e.getMessage());
                            }

                            // Cách 3: Gửi đến kênh chung - client subscribe '/notifications'
                            try {
                                messagingTemplate.convertAndSend(
                                        "/notifications",
                                        recipientDto
                                );
                                log.info("Sent notification to /notifications");
                            } catch (Exception e) {
                                log.error("Error sending to /notifications: {}", e.getMessage());
                            }

                            log.info("Notification processing completed for recipient: {}", task.getRecipientId());
                        } else {
                            log.warn("NotificationRecipient not found with ID: {}", task.getNotificationRecipientId());
                        }
                    } catch (Exception e) {
                        log.error("Error processing notification task: {}", e.getMessage(), e);
                    }
                    return null;
                });
            } catch (InterruptedException e) {
                log.warn("Notification worker interrupted", e);
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("Unexpected error in notification worker: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Đóng hàng đợi và giải phóng tài nguyên
     */
    public void shutdown() {
        executorService.shutdownNow();
        log.info("Notification work queue shutdown");
    }
}
