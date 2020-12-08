package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Order;

import java.math.BigDecimal;

public interface PricingService {
    BigDecimal calculatePrice(Order order);
}
