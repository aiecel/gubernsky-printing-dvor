package com.aiecel.gubernskyprintingdvor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ordered_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class OrderedProduct {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
