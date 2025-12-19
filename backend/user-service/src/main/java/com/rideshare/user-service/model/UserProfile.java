package com.rideshare.userservice.model;

import jakarta.persistence.*; 
import lombok.*;             
import java.time.LocalDateTime; 
import java.util.UUID;          
@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") 
    private UUID id; 

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String phone;

    @Column(nullable = false)
    private String role; 

    private Double rating = 5.0;
    private Integer totalRides = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}