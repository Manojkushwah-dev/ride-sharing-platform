package com.rideshare.paymentservice.service;

import com.rideshare.paymentservice.model.PaymentTransaction;
import com.rideshare.paymentservice.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentTransactionRepository repository;

    public void processWalletPayment(Long userId, Long rideId, Double amount) {

        // In real life, we would call User Service via EVENT or REST
        // For now assume wallet validation happens asynchronously

        PaymentTransaction transaction = PaymentTransaction.builder()
                .userId(userId)
                .rideId(rideId)
                .amount(amount)
                .paymentMode("WALLET")
                .status("SUCCESS")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(transaction);

        System.out.println("💰 Payment successful for ride " + rideId);
    }

    public void failPayment(Long userId, Long rideId, Double amount) {

        PaymentTransaction transaction = PaymentTransaction.builder()
                .userId(userId)
                .rideId(rideId)
                .amount(amount)
                .paymentMode("WALLET")
                .status("FAILED")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(transaction);
    }
}
