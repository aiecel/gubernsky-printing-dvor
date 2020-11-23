package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderedDocument;
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
        BigDecimal price = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

        //calculate price for all pages
        for (OrderedDocument orderedDocument : order.getOrderedDocuments()) {
            BigDecimal documentPrice = productService.getPageProduct().getPrice().multiply(new BigDecimal(orderedDocument.getDocument().getPages()));
            price = price.add(documentPrice.multiply(new BigDecimal(orderedDocument.getQuantity())));
        }

        //calculate price for all ordered products
        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
            price = price.add(orderedProduct.getProduct().getPrice().multiply(new BigDecimal(orderedProduct.getQuantity())));
        }

        return price;
    }
}
