package com.rideshare.rideservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RideRequest {
    private Long userId;
    private Long driverId;
    private String pickupLocation;
    private String dropLocation;
    private BigDecimal fare;
}
