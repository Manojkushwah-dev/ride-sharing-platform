package com.rideshare.rideservice.controller;

import com.rideshare.rideservice.dto.RideRequest;
import com.rideshare.rideservice.dto.RideResponse;
import com.rideshare.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public RideResponse createRide(@RequestBody RideRequest request) {
        return rideService.createRide(request);
    }

    @GetMapping("/user/{userId}")
    public List<RideResponse> getUserRides(@PathVariable Long userId) {
        return rideService.getRidesByUser(userId);
    }

    @GetMapping("/driver/{driverId}")
    public List<RideResponse> getDriverRides(@PathVariable Long driverId) {
        return rideService.getRidesByDriver(driverId);
    }

    // --- New Kafka booking endpoint ---
    @PostMapping("/book")
    public ResponseEntity<String> bookRide(@RequestBody RideRequest dto) {

        rideService.publishRideRequestedEvent(dto.getUserId(), dto.getPickupLocation(), dto.getDropLocation());
        return ResponseEntity.ok("Ride Requested");
    }
}
