package com.aiecel.gubernskytypography.bot.repository;

import com.aiecel.gubernskytypography.bot.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByCustomerId(String customerId);
    Optional<Cart> findByCustomerId(String customerId);
    void deleteByCustomerId(String customerId);
}
