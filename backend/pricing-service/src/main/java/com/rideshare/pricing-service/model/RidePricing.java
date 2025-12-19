package com.rideshare.pricingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RidePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rideId;

    private Double baseFare;
    private Double distanceKm;
    private Double timeMinutes;
    private Double surgeMultiplier;

    private Double totalFare;

    private LocalDateTime createdAt;
}
