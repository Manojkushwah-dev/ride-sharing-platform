package com.rideshare.notificationservice.service;

import com.rideshare.notificationservice.model.Notification;
import com.rideshare.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void sendNotification(Long userId, String type, String message) {

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .message(message)
                .status("SENT")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(notification);

        // Real-world: integrate email/SMS here
        System.out.println("📢 Notification sent to user " + userId + ": " + message);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return repository.findByUserId(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setStatus("READ");
        repository.save(notification);
    }
}
