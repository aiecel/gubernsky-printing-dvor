package com.aiecel.gubernskyprintingdvor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "page_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class PageProduct {
    public static final BigDecimal PAGE_PRODUCT_DEFAULT_PRICE = new BigDecimal(3);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price = PAGE_PRODUCT_DEFAULT_PRICE;
}
