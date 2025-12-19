package com.rideshare.paymentservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rideshare.paymentservice.dto.RideCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "ride-completed", groupId = "payment-group")
    public void handleRideCompleted(String message) {

        try {
            RideCompletedEvent event =
                    objectMapper.readValue(message, RideCompletedEvent.class);

            paymentService.processWalletPayment(
                    event.getUserId(),
                    event.getRideId(),
                    event.getFare()
            );

        } catch (Exception e) {
            System.err.println("❌ Payment failed: " + e.getMessage());
        }
    }
}
