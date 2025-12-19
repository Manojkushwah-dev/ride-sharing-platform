package com.rideshare.driverservice.repository;

import com.rideshare.driverservice.model.DriverProfile;
import com.rideshare.driverservice.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {

    Optional<DriverProfile> findByEmail(String email);

    List<DriverProfile> findByStatus(DriverStatus status);
}
