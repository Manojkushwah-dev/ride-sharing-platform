package com.rideshare.paymentservice.dto;

import lombok.Data;

@Data
public class RideCompletedEvent {
    private Long rideId;
    private Long userId;
    private Double fare;
}
