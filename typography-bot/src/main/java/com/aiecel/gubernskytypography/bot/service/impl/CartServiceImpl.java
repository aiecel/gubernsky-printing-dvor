package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.repository.CartRepository;
import com.aiecel.gubernskytypography.bot.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public boolean exists(String customerId) {
        return cartRepository.existsByCustomerId(customerId);
    }

    @Override
    public Optional<Cart> getByCustomerId(String customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(String customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }
}
