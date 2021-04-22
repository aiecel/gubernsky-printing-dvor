package com.aiecel.gubernskytypography.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartProduct extends CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String getName() {
        return product.getName();
    }
}
