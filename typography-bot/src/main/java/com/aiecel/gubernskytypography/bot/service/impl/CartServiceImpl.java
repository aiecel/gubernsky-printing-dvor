package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.repository.CartRepository;
import com.aiecel.gubernskytypography.bot.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public boolean exists(OffSiteUser customer) {
        return cartRepository.existsByCustomer(customer);
    }

    @Override
    public Optional<Cart> get(OffSiteUser customer) {
        return cartRepository.findByCustomer(customer);
    }

    @Override
    public Cart save(Cart cart) {
        log.info("Cart saved for user {}", cart.getCustomer());
        return cartRepository.save(cart);
    }

    @Override
    public void delete(OffSiteUser customer) {
        cartRepository.deleteByCustomer(customer);
        log.info("Cart deleted for user {}", customer);
    }
}
