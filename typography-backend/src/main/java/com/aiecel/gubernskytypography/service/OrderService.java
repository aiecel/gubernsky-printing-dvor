package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.Order;

import java.util.List;

public interface OrderService extends DAOService<Order> {
    List<Order> getAllByCustomerId(long customerId);
}
