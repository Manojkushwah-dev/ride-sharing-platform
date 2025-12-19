package com.rideshare.driverservice.dto;

import com.rideshare.driverservice.model.DriverStatus;
import lombok.Data;

@Data
public class DriverStatusRequest {
    private DriverStatus status;
}
