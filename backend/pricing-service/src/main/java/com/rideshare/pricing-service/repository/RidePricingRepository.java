package com.rideshare.pricingservice.repository;

import com.rideshare.pricingservice.model.RidePricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RidePricingRepository extends JpaRepository<RidePricing, Long> {
}
