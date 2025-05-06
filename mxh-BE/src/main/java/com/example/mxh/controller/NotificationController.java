//package com.example.mxh.controller;
//
//import com.example.mxh.exception.UserException;
//import com.example.mxh.map.NotificationMapper;
//import com.example.mxh.map.NotificationRecipientMapper;
//import com.example.mxh.model.notification.Notification;
//import com.example.mxh.model.notification.NotificationDto;
//import com.example.mxh.model.notification.NotificationRecipient;
//import com.example.mxh.model.notification.NotificationRecipientDto;
//import com.example.mxh.model.user.User;
//import com.example.mxh.service.notification.INotificationService;
//import com.example.mxh.service.user.IUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/notifications")
//@AllArgsConstructor
//public class NotificationController {
//
//    private final INotificationService notificationService;
//    private final IUserService userService;
//
//
//
//    @GetMapping
//    public ResponseEntity<List<NotificationRecipientDto>> getUserNotifications(@RequestHeader("Authorization") String jwt) throws UserException {
//        User user = userService.findUserByJwt(jwt);
//        List<NotificationRecipientDto> notifications = NotificationRecipientMapper.map(notificationService.getNotificationsForUser(user.getId()));
//        return ResponseEntity.ok(notifications);
//    }
//    @PutMapping("/read/{id}")
//    public ResponseEntity<NotificationDto> readNotification(@PathVariable("id")Long id){
//        NotificationDto dto = NotificationMapper.map(notificationService.readNotification(id));
//        return ResponseEntity.ok(dto);
//    }
//
//}
package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.map.NotificationMapper;
import com.example.mxh.map.NotificationRecipientMapper;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationDto;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationRecipientDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;
    private final IUserService userService;
    private final SimpMessagingTemplate messagingTemplate;
// get all notification of user unread
    @GetMapping
    public ResponseEntity<List<NotificationRecipientDto>> getUserNotifications(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        List<NotificationRecipientDto> notifications = NotificationRecipientMapper.map(notificationService.getNotificationsForUser(user.getId()));
        return ResponseEntity.ok(notifications);
    }
// get notification paged
    @GetMapping("/paged")
    public ResponseEntity<Page<NotificationRecipientDto>> getPagedNotifications(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws UserException {
        User user = userService.findUserByJwt(jwt);
        Pageable pageable = PageRequest.of(page, size);


        Page<NotificationRecipient> notificationsPage = notificationService.getPagedNotificationsForUser((long) user.getId(), pageable);


        Page<NotificationRecipientDto> notificationDtos = notificationsPage.map(notification -> {
            return NotificationRecipientMapper.map(List.of(notification)).get(0);
        });

        return ResponseEntity.ok(notificationDtos);
    }

// count unread notification
    @GetMapping("/unread-count")
    public ResponseEntity<Long> countUnreadNotifications(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        Long count = notificationService.countUnreadNotifications(user.getId());
        return ResponseEntity.ok(count);
    }
// read notification
    @PutMapping("/read/{id}")
    public ResponseEntity<NotificationDto> readNotification(@PathVariable("id") Long id) {
        NotificationDto dto = NotificationMapper.map(notificationService.readNotification(id));
        return ResponseEntity.ok(dto);
    }
// read all notification
    @PutMapping("/read-all")
    public ResponseEntity<Void> readAllNotifications(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        notificationService.markAllNotificationsAsRead(user.getId());
        return ResponseEntity.ok().build();
    }
// create notification and send real-time notification
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        Notification notification = notificationService.createNotification(NotificationMapper.map(notificationDto));

        // Gửi thông báo real-time đến người nhận
        for (Long recipientId : notificationDto.getRecipientIds()) {
            messagingTemplate.convertAndSendToUser(
                    recipientId.toString(),
                    "/notifications",
                    NotificationMapper.map(notification)
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(NotificationMapper.map(notification));
    }
// delete notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
// filter notification( lọc thông báo theo type và read)
    @GetMapping("/filter")
    public ResponseEntity<List<NotificationRecipientDto>> filterNotifications(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean read) throws UserException {
        User user = userService.findUserByJwt(jwt);
        List<NotificationRecipient> notifications = notificationService.filterNotifications(user.getId(), type, read);
        return ResponseEntity.ok(NotificationRecipientMapper.map(notifications));
    }
}
