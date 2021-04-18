package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.dto.ProductDTO;
import com.aiecel.gubernskytypography.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllDTOs();
    }
}
