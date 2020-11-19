package com.aiecel.gubernskyprintingdvor.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<Document> documents;

    @OneToMany(mappedBy = "order")
    private List<OrderedProduct> orderedProducts;

    private String comment;

    private ZonedDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.DEFAULT;

    private BigDecimal price;

    private boolean isPaid;
}
