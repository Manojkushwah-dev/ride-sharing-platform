package com.rideshare.rideservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RideResponse {
    private Long rideId;
    private Long userId;
    private Long driverId;
    private String pickupLocation;
    private String dropLocation;
    private BigDecimal fare;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
