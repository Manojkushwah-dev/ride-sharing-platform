package com.rideshare.driverservice.dto;

import lombok.Data;

@Data
public class VehicleRequest {
    private String vehicleNumber;
    private String type;
    private String model;
}
