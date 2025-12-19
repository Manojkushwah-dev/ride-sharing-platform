package com.rideshare.rideservice.service;

import com.rideshare.rideservice.dto.RideRequest;
import com.rideshare.rideservice.dto.RideResponse;
import com.rideshare.rideservice.model.Ride;
import com.rideshare.rideservice.repository.RideRepository;
import com.rideshare.rideservice.kafka.RideEventProducer;
import com.rideshare.common.events.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideEventProducer rideEventProducer;

    public RideResponse createRide(RideRequest request) {
        Ride ride = new Ride();
        ride.setUserId(request.getUserId());
        ride.setDriverId(request.getDriverId());
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setFare(request.getFare());
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(LocalDateTime.now());
        ride.setUpdatedAt(LocalDateTime.now());

        Ride saved = rideRepository.save(ride);

        // Publish Kafka Event
        RideRequestedEvent event = new RideRequestedEvent(
                saved.getId().toString(),
                saved.getUserId().toString(),
                saved.getPickupLocation(),
                saved.getDropLocation()
        );

        rideEventProducer.publishRideRequested(event);

        return mapToResponse(saved);
    }

    public List<RideResponse> getRidesByUser(Long userId) {
        return rideRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RideResponse> getRidesByDriver(Long driverId) {
        return rideRepository.findByDriverId(driverId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RideResponse mapToResponse(Ride ride) {
        RideResponse response = new RideResponse();
        response.setRideId(ride.getId());
        response.setUserId(ride.getUserId());
        response.setDriverId(ride.getDriverId());
        response.setPickupLocation(ride.getPickupLocation());
        response.setDropLocation(ride.getDropLocation());
        response.setFare(ride.getFare());
        response.setStatus(ride.getStatus());
        response.setCreatedAt(ride.getCreatedAt());
        response.setUpdatedAt(ride.getUpdatedAt());
        return response;
    }

    // --- New method for publishing ride requests via Kafka ---
    public void publishRideRequestedEvent(Long userId, String pickup, String drop) {
        RideRequestedEvent event = new RideRequestedEvent(
                UUID.randomUUID().toString(),
                userId.toString(),
                pickup,
                drop
        );
        rideEventProducer.publishRideRequested(event);
    }
}
