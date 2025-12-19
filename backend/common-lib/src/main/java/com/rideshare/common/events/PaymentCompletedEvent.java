package com.rideshare.common.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {
    private String rideId;
    private String userId;
    private String status;
}
