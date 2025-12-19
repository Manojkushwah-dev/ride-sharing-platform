package com.rideshare.notificationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String type;   // RIDE, PAYMENT, SYSTEM
    private String message;

    private String status; // SENT, READ

    private LocalDateTime createdAt;
}
