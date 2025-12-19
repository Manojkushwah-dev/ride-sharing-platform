package com.rideshare.driverservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String phone;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    private Double rating = 5.0;
    private Integer totalRides = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
