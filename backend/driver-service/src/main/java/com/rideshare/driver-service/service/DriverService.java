package com.rideshare.driverservice.service;

import com.rideshare.driverservice.dto.DriverStatusRequest;
import com.rideshare.driverservice.dto.VehicleRequest;
import com.rideshare.driverservice.model.*;
import com.rideshare.driverservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverProfileRepository driverRepo;
    private final VehicleRepository vehicleRepo;

    public DriverProfile getProfile(String email) {
        return driverRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public DriverProfile updateStatus(String email, DriverStatusRequest request) {
        DriverProfile driver = getProfile(email);

        driver.setStatus(request.getStatus());
        driver.setUpdatedAt(LocalDateTime.now());

        return driverRepo.save(driver);
    }

    public Vehicle addOrUpdateVehicle(String email, VehicleRequest request) {
        DriverProfile driver = getProfile(email);

        Vehicle vehicle = vehicleRepo.findByDriverId(driver.getId())
                .orElse(Vehicle.builder().driverId(driver.getId()).build());

        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setType(request.getType());
        vehicle.setModel(request.getModel());

        return vehicleRepo.save(vehicle);
    }

    public List<DriverProfile> findAvailableDrivers() {
        return driverRepo.findByStatus(DriverStatus.ONLINE);
    }
}
