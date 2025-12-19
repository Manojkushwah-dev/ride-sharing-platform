package com.rideshare.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "ride-events", groupId = "notification-group")
    public void consumeRideEvents(String message) {
        // Example message parsing simplified
        notificationService.sendNotification(
                1L,
                "RIDE",
                message
        );
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consumePaymentEvents(String message) {
        notificationService.sendNotification(
                1L,
                "PAYMENT",
                message
        );
    }
}
