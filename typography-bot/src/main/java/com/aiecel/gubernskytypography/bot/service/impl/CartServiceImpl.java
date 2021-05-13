package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.CartProduct;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.model.Product;
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
    public Optional<Cart> find(OffSiteUser customer) {
        return cartRepository.findByCustomer(customer);
    }

    @Override
    public Cart findOrCreate(OffSiteUser customer) {
        return find(customer).orElseGet(() -> {
            log.info("Creating cart for user {}", customer);
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            return cartRepository.save(newCart);
        });
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

    @Override
    public Cart addProductToCart(OffSiteUser customer, Product product, int quantity) {
        //get cart or create new
        Cart cart = findOrCreate(customer);

        //construct cartProduct
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);

        //add product to cart
        cart.addItem(cartProduct);

        log.info("User {} added {} to cart", customer, cartProduct);

        return cartRepository.save(cart);
    }

    @Override
    public Cart attachCommentToCart(OffSiteUser customer, String comment) {
        log.info("Attaching comment '{}' to cart of user {}", comment, customer);
        Cart cart = findOrCreate(customer);
        cart.setComment(comment);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeCommentFromCart(OffSiteUser customer) {
        log.info("Removing comment from cart of user {}", customer);
        Cart cart = findOrCreate(customer);
        cart.removeComment();
        return cartRepository.save(cart);
    }
}
