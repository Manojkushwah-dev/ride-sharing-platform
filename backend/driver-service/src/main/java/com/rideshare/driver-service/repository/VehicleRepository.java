package com.rideshare.driverservice.repository;

import com.rideshare.driverservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByDriverId(Long driverId);
}
