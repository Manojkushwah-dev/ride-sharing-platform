package com.rideshare.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID; // Added import

@Entity
@Table(name = "user_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Added missing type 'Long'

    @Column(nullable = false)
    private UUID userId; // Changed from Long to UUID to match UserProfile.id

    private Long rideId;

    private Integer rating;
    private String comment;

    private LocalDateTime createdAt;
}