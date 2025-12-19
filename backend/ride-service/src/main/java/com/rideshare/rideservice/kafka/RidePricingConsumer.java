package com.rideshare.rideservice.kafka;

import com.rideshare.common.events.PriceCalculatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RidePricingConsumer {

    @KafkaListener(topics = "ride.priced", groupId = "ride-group")
    public void handlePrice(PriceCalculatedEvent event) {
        // Update ride price in DB
        System.out.println("Price received for ride " + event.getRideId() +
                " : " + event.getPrice());
    }
}
