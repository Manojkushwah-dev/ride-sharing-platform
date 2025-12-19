package com.rideshare.common.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideCompletedEvent {
    private String rideId;
    private String userId;
}
