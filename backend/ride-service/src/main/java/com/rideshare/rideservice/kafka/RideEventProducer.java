package com.rideshare.rideservice.kafka;

import com.rideshare.common.events.RideRequestedEvent;
import com.rideshare.common.events.RideCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRideRequested(RideRequestedEvent event) {
        kafkaTemplate.send("ride.requested", event.getRideId(), event);
    }

    public void publishRideCompleted(RideCompletedEvent event) {
        kafkaTemplate.send("ride.completed", event.getRideId(), event);
    }
}
