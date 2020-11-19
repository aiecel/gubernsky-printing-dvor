package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Product;

import java.util.List;

public interface ProductService extends DAOService<Product> {
    Product getPageProduct();
    List<Product> getAll();
}
