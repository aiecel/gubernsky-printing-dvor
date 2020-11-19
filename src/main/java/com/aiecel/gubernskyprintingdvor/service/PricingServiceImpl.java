package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Document;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingServiceImpl implements PricingService {
    private final ProductService productService;

    @Autowired
    public PricingServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public BigDecimal calculatePrice(Order order) {
        BigDecimal price = BigDecimal.ZERO;

        //calculate price for all pages
        for (Document document : order.getDocuments()) {
            price = price.add(productService.getPageProduct().getPrice().multiply(new BigDecimal(document.getPages())));
        }

        //calculate price for all ordered products
        for (OrderedProduct product : order.getOrderedProducts()) {
            price = price.add(product.getPrice());
        }

        return price;
    }
}
