package com.example.mxh.service.notification;

import com.example.mxh.model.notification.NotificationTask;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
@Service

public class NotificationWorkQueu {
    private final BlockingQueue<NotificationTask> taskQueue = new LinkedBlockingQueue<>();

    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    @Autowired
    public NotificationWorkQueu(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        // Khởi chạy các worker
        for (int i = 0; i < 5; i++) {
            executorService.submit(this::processQueue);
        }
    }
    public void enqueue(NotificationTask task) {
        taskQueue.offer(task);
    }


    private void processQueue() {
        while (true) {
            try {
                NotificationTask task = taskQueue.take();
                messagingTemplate.convertAndSend("/topic/notifications/" + task.getRecipientId(),
                        task.getMessageContent());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
