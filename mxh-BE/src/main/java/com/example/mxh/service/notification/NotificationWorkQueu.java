//package com.example.mxh.service.notification;
//
//import com.example.mxh.model.notification.NotificationTask;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//@Service
//
//public class NotificationWorkQueu {
//    private final BlockingQueue<NotificationTask> taskQueue = new LinkedBlockingQueue<>();
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
//    @Autowired
//    public NotificationWorkQueu(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//        // Khởi chạy các worker
//        for (int i = 0; i < 5; i++) {
//            executorService.submit(this::processQueue);
//        }
//    }
//    public void enqueue(NotificationTask task) {
//        taskQueue.offer(task);
//    }
//
//
//    private void processQueue() {
//        while (true) {
//            try {
//                NotificationTask task = taskQueue.take();
//                messagingTemplate.convertAndSend("/topic/notifications/" + task.getRecipientId(),
//                        task.getMessageContent());
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    }
//
//}
package com.example.mxh.service.notification;

import com.example.mxh.map.NotificationMapper;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationDto;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationTask;
import com.example.mxh.repository.NotificationRecipientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
    private final ExecutorService executorService;
    private final NotificationRecipientRepository notificationRecipientRepository;

    @Autowired
    public NotificationWorkQueu(
            SimpMessagingTemplate messagingTemplate,
            NotificationRecipientRepository notificationRecipientRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRecipientRepository = notificationRecipientRepository;
        this.executorService = Executors.newFixedThreadPool(5);

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
    @Transactional
    private void processQueue() {
        while (true) {
            try {
                NotificationTask task = taskQueue.take();
                log.debug("Processing notification task for recipient: {}", task.getRecipientId());

                try {
                    // Lấy thông tin đầy đủ về thông báo từ database
                    Optional<NotificationRecipient> recipientOpt =
                            notificationRecipientRepository.findById(task.getNotificationRecipientId());

                    if (recipientOpt.isPresent()) {
                        NotificationRecipient recipient = recipientOpt.get();
                        Notification notification = recipient.getNotification();

                        // Tạo DTO để gửi qua WebSocket
                        NotificationDto notificationDto = new NotificationDto();
                        notificationDto.setId(notification.getId());
                        notificationDto.setNotification(notification.getMessage());
                        notificationDto.setReceivedAt(recipient.getReceivedAt());

                        // Gửi thông báo qua cả hai kênh để đảm bảo tương thích
                        // 1. Gửi qua kênh /user/{userId}/notifications (cách mới)
                        messagingTemplate.convertAndSendToUser(
                                task.getRecipientId().toString(),
                                "/notifications",
                                notificationDto
                        );

                        // 2. Gửi qua kênh /topic/notifications/{userId} (cách cũ)
                        messagingTemplate.convertAndSend(
                                "/topic/notifications/" + task.getRecipientId(),
                                notificationDto
                        );

                        log.debug("Notification sent to recipient: {}", task.getRecipientId());
                    } else {
                        log.warn("NotificationRecipient not found with ID: {}", task.getNotificationRecipientId());
                    }
                } catch (Exception e) {
                    log.error("Error processing notification task: {}", e.getMessage(), e);
                }
            } catch (InterruptedException e) {
                log.warn("Notification worker interrupted", e);
                Thread.currentThread().interrupt();
                break;
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
