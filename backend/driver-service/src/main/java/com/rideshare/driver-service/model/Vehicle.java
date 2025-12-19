package com.rideshare.driverservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long driverId;

    private String vehicleNumber;
    private String type; // BIKE, CAR, AUTO
    private String model;
}
