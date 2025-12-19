package com.rideshare.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long rideId;

    private Double amount;

    private String status; // SUCCESS / FAILED
    private String paymentMode; // WALLET

    private LocalDateTime createdAt;
}
