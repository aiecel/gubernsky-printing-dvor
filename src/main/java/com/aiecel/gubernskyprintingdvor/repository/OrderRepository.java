package com.aiecel.gubernskyprintingdvor.repository;

import com.aiecel.gubernskyprintingdvor.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
