package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.PageProduct;
import com.aiecel.gubernskyprintingdvor.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends DAOService<Product> {
    List<Product> getAll();
    Optional<Product> getProduct(String name);
    PageProduct getPageProduct();
    PageProduct savePageProduct(PageProduct pageProduct);
}
