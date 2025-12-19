package com.rideshare.pricingservice.dto;

import lombok.Data;

@Data
public class FareRequest {
    private Long rideId;
    private Double distanceKm;
    private Double timeMinutes;
}
