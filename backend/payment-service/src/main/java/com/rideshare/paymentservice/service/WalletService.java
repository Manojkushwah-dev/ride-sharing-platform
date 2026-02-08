package com.rideshare.paymentservice.service;

import com.rideshare.paymentservice.dto.AddMoneyRequest;
import com.rideshare.paymentservice.model.PaymentTransaction;
import com.rideshare.paymentservice.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final PaymentTransactionRepository paymentRepository;
    private final RestTemplate restTemplate;

    /**
     * Add money to user's wallet
     * This calls the user-service to update the wallet balance
     * and creates a CREDIT transaction record
     */
    public PaymentTransaction addMoney(String userEmail, AddMoneyRequest request) {
        try {
            // Call user-service to add money to wallet
            String userServiceUrl = "http://user-service/api/v1/users/me/wallet/add";
            
            // Create request body for user-service
            java.util.Map<String, Object> walletRequest = new java.util.HashMap<>();
            walletRequest.put("amount", request.getAmount());
            
            // Set headers
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-USER-EMAIL", userEmail);
            headers.set("Content-Type", "application/json");
            
            org.springframework.http.HttpEntity<java.util.Map<String, Object>> entity = 
                new org.springframework.http.HttpEntity<>(walletRequest, headers);
            
            // Make the call to user-service
            restTemplate.exchange(
                    userServiceUrl,
                    HttpMethod.POST,
                    entity,
                    Object.class
            );
            
            // Create a CREDIT transaction record
            PaymentTransaction transaction = PaymentTransaction.builder()
                    .userId(null) // Will be set if we can extract from user-service response
                    .rideId(null) // Not a ride payment
                    .amount(request.getAmount().doubleValue())
                    .status("SUCCESS")
                    .paymentMode("WALLET_CREDIT")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            return paymentRepository.save(transaction);
            
        } catch (Exception e) {
            // Create a FAILED transaction record
            PaymentTransaction transaction = PaymentTransaction.builder()
                    .userId(null)
                    .rideId(null)
                    .amount(request.getAmount().doubleValue())
                    .status("FAILED")
                    .paymentMode("WALLET_CREDIT")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            paymentRepository.save(transaction);
            throw new RuntimeException("Failed to add money to wallet: " + e.getMessage(), e);
        }
    }
}

