package com.rideshare.pricingservice.dto;

import lombok.Data;

@Data
public class LocationFareRequest {
    private String pickupLocation;
    private String dropoffLocation;
    private Long rideId; // Optional, for associating with a ride
}

