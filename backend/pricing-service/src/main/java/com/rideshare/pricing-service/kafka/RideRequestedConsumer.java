package com.rideshare.pricingservice.kafka;

import com.rideshare.common.events.RideRequestedEvent;
import com.rideshare.common.events.PriceCalculatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestedConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "ride.requested", groupId = "pricing-group")
    public void calculatePrice(RideRequestedEvent event) {

        double price = 150.0; // dummy logic for now

        System.out.println("💰 Price calculated for ride " + event.getRideId());

        PriceCalculatedEvent priceEvent =
                new PriceCalculatedEvent(event.getRideId(), price);

        kafkaTemplate.send("ride.priced", event.getRideId(), priceEvent);
    }
}
