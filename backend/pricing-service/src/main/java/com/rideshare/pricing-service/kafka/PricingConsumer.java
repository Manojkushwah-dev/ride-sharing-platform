package com.rideshare.pricing.kafka;

import com.rideshare.common.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import com.rideshare.common.events.RideRequestedEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "ride.requested", groupId = "pricing-group")
    public void calculatePrice(RideRequestedEvent event) {

        double price = 100 + Math.random() * 50;

        PriceCalculatedEvent priceEvent =
                new PriceCalculatedEvent(event.getRideId(), price);

        kafkaTemplate.send("ride.priced", event.getRideId(), priceEvent);
    }
}
