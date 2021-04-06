package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer_Id(long customerId);
}
