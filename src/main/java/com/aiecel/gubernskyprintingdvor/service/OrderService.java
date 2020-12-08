package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Order;

import java.util.List;

public interface OrderService extends DAOService<Order> {
    List<Order> getAllByCustomerId(long customerId);
}
