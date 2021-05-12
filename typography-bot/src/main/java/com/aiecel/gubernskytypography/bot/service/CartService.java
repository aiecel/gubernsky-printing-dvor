package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;

import java.util.Optional;

public interface CartService {
    boolean exists(OffSiteUser customer);
    Optional<Cart> get(OffSiteUser customer);
    Cart save(Cart cart);
    void delete(OffSiteUser customer);
}
