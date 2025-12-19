package com.rideshare.paymentservice.controller;

import com.rideshare.paymentservice.model.PaymentTransaction;
import com.rideshare.paymentservice.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentTransactionRepository repository;

    @GetMapping
    public List<PaymentTransaction> getAllPayments() {
        return repository.findAll();
    }
}
