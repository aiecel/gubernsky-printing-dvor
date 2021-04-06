package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.PageProduct;
import com.aiecel.gubernskytypography.model.Product;
import com.aiecel.gubernskytypography.repository.PageProductRepository;
import com.aiecel.gubernskytypography.repository.ProductRepository;
import com.aiecel.gubernskytypography.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PageProductRepository pageProductRepository;

    public ProductServiceImpl(ProductRepository productRepository, PageProductRepository pageProductRepository) {
        this.productRepository = productRepository;
        this.pageProductRepository = pageProductRepository;
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

    @Override
    public PageProduct getPageProduct() {
        return pageProductRepository.findById(0L).orElseGet(() -> pageProductRepository.save(new PageProduct()));
    }

    @Override
    public PageProduct savePageProduct(PageProduct pageProduct) {
        pageProduct.setId(0L);
        return pageProductRepository.save(pageProduct);
    }
}
