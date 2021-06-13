package com.aiecel.gubernskytypography.bot.repository;

import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByCustomer(OffSiteUser customer);
    Optional<Cart> findByCustomer(OffSiteUser customer);
    void deleteByCustomer(OffSiteUser customer);
}
