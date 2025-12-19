package com.rideshare.notification.kafka;

import com.rideshare.common.events.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void notifyUser(PaymentCompletedEvent event) {
        System.out.println(
            "Notification sent for Ride " + event.getRideId() +
            " Payment Status: " + event.getStatus()
        );
    }
}
