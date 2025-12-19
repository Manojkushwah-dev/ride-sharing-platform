package com.rideshare.pricingservice.controller;

import com.rideshare.pricingservice.dto.*;
import com.rideshare.pricingservice.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @PostMapping("/estimate")
    public FareResponse estimateFare(@RequestBody FareRequest request) {
        return pricingService.calculateFare(request);
    }

    @PostMapping("/calculate")
    public FareResponse calculateFare(@RequestBody FareRequest request) {
        return pricingService.calculateFare(request);
    }
}
