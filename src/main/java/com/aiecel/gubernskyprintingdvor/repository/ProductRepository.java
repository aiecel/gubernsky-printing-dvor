package com.aiecel.gubernskyprintingdvor.repository;

import com.aiecel.gubernskyprintingdvor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
