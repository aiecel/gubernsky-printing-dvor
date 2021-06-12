package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.dto.ProductDTO;
import com.aiecel.gubernskytypography.dto.mapping.ProductDTOMapper;
import com.aiecel.gubernskytypography.model.Page;
import com.aiecel.gubernskytypography.model.Product;
import com.aiecel.gubernskytypography.repository.PageRepository;
import com.aiecel.gubernskytypography.repository.ProductRepository;
import com.aiecel.gubernskytypography.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PageRepository pageRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllDTOs() {
        return getAll().stream()
                .map(product -> Mappers.getMapper(ProductDTOMapper.class).toDto(product))
                .collect(Collectors.toList());
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
    public Page getPageProduct() {
        return pageRepository.findById(0L).orElseGet(() -> pageRepository.save(new Page()));
    }

    @Override
    public Page savePageProduct(Page page) {
        page.setId(0L);
        return pageRepository.save(page);
    }
}
