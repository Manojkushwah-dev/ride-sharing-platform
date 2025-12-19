package com.rideshare.userservice.dto;

import lombok.Data;

@Data
public class RatingRequest {
    private Long rideId;
    private Integer rating;
    private String comment;
}
