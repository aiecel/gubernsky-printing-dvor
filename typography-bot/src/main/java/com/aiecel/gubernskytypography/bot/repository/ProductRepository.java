package com.aiecel.gubernskytypography.bot.repository;

import com.aiecel.gubernskytypography.bot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
