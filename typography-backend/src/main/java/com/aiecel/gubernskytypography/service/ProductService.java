package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.PageProduct;
import com.aiecel.gubernskytypography.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends DAOService<Product> {
    List<Product> getAll();
    Optional<Product> getProduct(String name);
    PageProduct getPageProduct();
    PageProduct savePageProduct(PageProduct pageProduct);
}
