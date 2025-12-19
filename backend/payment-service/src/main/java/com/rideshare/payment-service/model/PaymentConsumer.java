package com.rideshare.payment.kafka;

import com.rideshare.common.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "ride.completed", groupId = "payment-group")
    public void processPayment(RideCompletedEvent event) {

        PaymentCompletedEvent paymentEvent =
                new PaymentCompletedEvent(
                        event.getRideId(),
                        event.getUserId(),
                        "SUCCESS"
                );

        kafkaTemplate.send("payment.completed", event.getRideId(), paymentEvent);
    }
}
