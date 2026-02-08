package com.rideshare.paymentservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddMoneyRequest {
    private BigDecimal amount;
}

