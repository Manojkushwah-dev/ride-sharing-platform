package com.rideshare.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime updatedAt;
}
