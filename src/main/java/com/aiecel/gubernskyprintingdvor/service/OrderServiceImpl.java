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
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public Order save(Order order) {
        Order savedOrder = orderRepository.save(order);
        log.info("New order! - {}", order);
        return savedOrder;
    }
}
