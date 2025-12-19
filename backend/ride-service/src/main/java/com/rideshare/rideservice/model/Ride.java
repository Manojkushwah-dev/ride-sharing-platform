package com.rideshare.rideservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long driverId;
    private String pickupLocation;
    private String dropLocation;
    private BigDecimal fare;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
