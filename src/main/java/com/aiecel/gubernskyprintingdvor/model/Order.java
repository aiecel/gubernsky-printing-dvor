package com.aiecel.gubernskyprintingdvor.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedDocument> orderedDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    private String comment;

    private ZonedDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.DEFAULT;

    private BigDecimal price;

    private boolean isPaid;

    public void addOrderedProduct(OrderedProduct orderedProduct) {
        for (OrderedProduct product : orderedProducts) {
            if (product.getProduct().getName().equals(orderedProduct.getProduct().getName())) {
                product.setQuantity(product.getQuantity() + orderedProduct.getQuantity());
                return;
            }
        }
        orderedProducts.add(orderedProduct);
    }

    public boolean isEmpty() {
        return orderedDocuments.size() == 0 && orderedProducts.size() == 0 && (comment == null || comment.length() == 0);
    }
}
