package com.rideshare.pricingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FareResponse {
    private Double totalFare;
    private Double surgeMultiplier;
}
