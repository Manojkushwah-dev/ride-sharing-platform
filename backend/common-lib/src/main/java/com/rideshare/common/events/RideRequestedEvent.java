package com.rideshare.common.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestedEvent {
    private String rideId;
    private String userId;
    private String pickupLocation;
    private String dropLocation;
}
