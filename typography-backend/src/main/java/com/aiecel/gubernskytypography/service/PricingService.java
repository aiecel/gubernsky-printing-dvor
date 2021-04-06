package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.Order;

import java.math.BigDecimal;

public interface PricingService {
    BigDecimal calculatePrice(Order order);
}
