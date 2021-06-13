package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.model.Product;

import java.util.Optional;

public interface CartService {
    boolean exists(OffSiteUser customer);

    Optional<Cart> find(OffSiteUser customer);

    Cart findOrCreate(OffSiteUser customer);

    Cart save(Cart cart);

    void delete(OffSiteUser customer);

    Cart addProductToCart(OffSiteUser customer, Product product, int quantity);

    Cart attachCommentToCart(OffSiteUser customer, String comment);

    Cart removeCommentFromCart(OffSiteUser customer);
}
