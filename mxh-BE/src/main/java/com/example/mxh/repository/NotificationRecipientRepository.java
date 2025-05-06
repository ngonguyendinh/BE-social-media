package com.example.mxh.repository;

import com.example.mxh.model.notification.NotificationRecipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
    List<NotificationRecipient> findByRecipientIdAndIsReadFalse(int recipientId);

    Page<NotificationRecipient> findByRecipientId(Long recipientId, Pageable pageable);

    List<NotificationRecipient> findByRecipientId(Long recipientId);

    Long countByRecipientIdAndIsReadFalse(int recipientId);

    @Modifying
    @Query("UPDATE NotificationRecipient nr SET nr.isRead = true WHERE nr.recipient.id = :userId AND nr.isRead = false")
    void markAllAsReadForUser(@Param("userId") int userId);

    @Modifying
    @Query("DELETE FROM NotificationRecipient nr WHERE nr.notification.id = :notificationId")
    void deleteByNotificationId(@Param("notificationId") Long notificationId);

    List<NotificationRecipient> findByRecipientIdAndNotificationTypeAndIsRead(int recipientId, String type, boolean isRead);

    List<NotificationRecipient> findByRecipientIdAndNotificationType(int recipientId, String type);

    List<NotificationRecipient> findByRecipientIdAndIsRead(int recipientId, boolean isRead);
}
