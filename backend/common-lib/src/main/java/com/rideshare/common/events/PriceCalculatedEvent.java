package com.rideshare.common.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculatedEvent {
    private String rideId;
    private double price;
}
