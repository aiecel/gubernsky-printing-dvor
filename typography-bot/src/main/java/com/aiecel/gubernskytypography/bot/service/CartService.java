package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.model.Cart;

import java.util.Optional;

public interface CartService {
    boolean exists(String customerId);
    Optional<Cart> getByCustomerId(String customerId);
    Cart save(Cart cart);
    void delete(String customerId);
}
