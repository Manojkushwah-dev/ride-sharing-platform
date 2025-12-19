package com.rideshare.pricingservice.service;

import com.rideshare.pricingservice.dto.FareRequest;
import com.rideshare.pricingservice.dto.FareResponse;
import com.rideshare.pricingservice.model.RidePricing;
import com.rideshare.pricingservice.repository.RidePricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final RidePricingRepository repository;

    // constants (can come from config later)
    private static final double BASE_FARE = 50;
    private static final double RATE_PER_KM = 12;
    private static final double RATE_PER_MIN = 2;

    public FareResponse calculateFare(FareRequest request) {

        double surge = determineSurge();
        double total = (BASE_FARE
                + (request.getDistanceKm() * RATE_PER_KM)
                + (request.getTimeMinutes() * RATE_PER_MIN))
                * surge;

        RidePricing pricing = RidePricing.builder()
                .rideId(request.getRideId())
                .baseFare(BASE_FARE)
                .distanceKm(request.getDistanceKm())
                .timeMinutes(request.getTimeMinutes())
                .surgeMultiplier(surge)
                .totalFare(total)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(pricing);

        return new FareResponse(total, surge);
    }

    private double determineSurge() {
        // Simple logic for now
        return Math.random() > 0.7 ? 1.5 : 1.0;
    }
}
