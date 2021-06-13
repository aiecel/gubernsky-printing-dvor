package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.Role;
import com.aiecel.gubernskytypography.notification.NewOrderNotification;
import com.aiecel.gubernskytypography.repository.OrderRepository;
import com.aiecel.gubernskytypography.service.NotificationService;
import com.aiecel.gubernskytypography.service.OrderService;
import com.aiecel.gubernskytypography.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Order save(Order order) {
        notificationService.sendNotification(new NewOrderNotification(order), userService.getUsersWithRole(Role.ADMIN));
        log.info("New order! - {}", order);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllByCustomerId(long customerId) {
        return orderRepository.findAllByCustomer_Id(customerId);
    }
}
