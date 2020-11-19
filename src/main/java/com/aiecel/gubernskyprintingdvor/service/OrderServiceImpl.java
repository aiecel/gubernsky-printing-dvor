package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PricingService pricingService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PricingService pricingService) {
        this.orderRepository = orderRepository;
        this.pricingService = pricingService;
    }

    @Override
    public Order save(Order order) {
        order.setPrice(pricingService.calculatePrice(order));
        log.info("New order! - {}", order);
        return orderRepository.save(order);
    }
}
