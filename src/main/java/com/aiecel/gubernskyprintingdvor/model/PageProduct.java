package com.aiecel.gubernskyprintingdvor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @GeneratedValue
    private long id;

    private BigDecimal price = PAGE_PRODUCT_DEFAULT_PRICE;
}
