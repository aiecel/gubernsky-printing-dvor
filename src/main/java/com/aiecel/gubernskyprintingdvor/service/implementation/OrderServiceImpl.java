package com.aiecel.gubernskyprintingdvor.service.implementation;

import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.notification.NewOrderNotification;
import com.aiecel.gubernskyprintingdvor.repository.OrderRepository;
import com.aiecel.gubernskyprintingdvor.service.NotificationService;
import com.aiecel.gubernskyprintingdvor.service.OrderService;
import com.aiecel.gubernskyprintingdvor.service.PricingService;
import com.aiecel.gubernskyprintingdvor.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PricingService pricingService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Order save(Order order) {
        order.setPrice(pricingService.calculatePrice(order));
        notificationService.sendNotification(new NewOrderNotification(order), userService.getAdministrators());
        log.info("New order! - {}", order);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllByCustomerId(long customerId) {
        return orderRepository.findAllByCustomer_Id(customerId);
    }
}
