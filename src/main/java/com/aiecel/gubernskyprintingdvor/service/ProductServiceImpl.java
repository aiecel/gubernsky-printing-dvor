package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Product;
import com.aiecel.gubernskyprintingdvor.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getPageProduct() {
        return productRepository.findByName(Product.PAGE_PRODUCT_NAME).orElseGet(() -> {
            Product pageProduct = new Product();
            pageProduct.setName(Product.PAGE_PRODUCT_NAME);
            pageProduct.setPrice(Product.PAGE_PRODUCT_DEFAULT_PRICE);
            return save(pageProduct);
        });
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("New product saved - {}", product);
        return savedProduct;
    }
}
