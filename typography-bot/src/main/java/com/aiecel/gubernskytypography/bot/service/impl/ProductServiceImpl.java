package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.dto.ProductDTO;
import com.aiecel.gubernskytypography.bot.dto.mapping.ProductDTOMapper;
import com.aiecel.gubernskytypography.bot.model.Product;
import com.aiecel.gubernskytypography.bot.repository.ProductRepository;
import com.aiecel.gubernskytypography.bot.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public static final Duration AVAILABLE_PRODUCTS_UPDATE_DURATION = Duration.ofMinutes(1);
    public static final String ALL_PRODUCTS_ENDPOINT_URL = "http://localhost:8080/products/all";

    private final WebClient webClient;
    private final ProductRepository productRepository;

    private ZonedDateTime availableProductsUpdateDateTime = ZonedDateTime.now();

    @Override
    public List<Product> getAll() {
        //update available products if needed
        if (Duration.between(availableProductsUpdateDateTime, ZonedDateTime.now())
                .compareTo(AVAILABLE_PRODUCTS_UPDATE_DURATION) > 0) {
            updateProducts();
        }
        return productRepository.findAll();
    }

    @PostConstruct
    private void updateProducts() {
        Optional<ProductDTO[]> productDTOS = webClient
                .get()
                .uri(ALL_PRODUCTS_ENDPOINT_URL)
                .retrieve()
                .bodyToMono(ProductDTO[].class)
                .blockOptional();

        productRepository.saveAll(
                productDTOS
                        .map(dtos -> Arrays.stream(dtos)
                                .map(productDTO -> Mappers.getMapper(ProductDTOMapper.class).toEntity(productDTO))
                                .collect(Collectors.toSet()))
                        .orElseGet(HashSet::new)
        );//todo handle exceptions

        availableProductsUpdateDateTime = ZonedDateTime.now();
        log.info("Products updated");
    }
}
