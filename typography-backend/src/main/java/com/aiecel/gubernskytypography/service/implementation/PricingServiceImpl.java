package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.OrderedDocument;
import com.aiecel.gubernskytypography.model.OrderedProduct;
import com.aiecel.gubernskytypography.service.PricingService;
import com.aiecel.gubernskytypography.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingServiceImpl implements PricingService {
    private final ProductService productService;

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
