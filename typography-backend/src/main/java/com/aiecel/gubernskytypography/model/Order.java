package com.aiecel.gubernskytypography.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderedItem> orderedItems = new HashSet<>();

    private String comment;

    private ZonedDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.DEFAULT;

    private BigDecimal price;

    private boolean isPaid;

    public void addItem(OrderedItem item) {
        for (OrderedItem orderedItem : orderedItems) {
            if (item instanceof OrderedDocument && orderedItem instanceof OrderedDocument) {
                Document itemDocument = ((OrderedDocument) item).getDocument();
                Document orderedDocument = ((OrderedDocument) orderedItem).getDocument();
                if (itemDocument.equals(orderedDocument)) {
                    orderedItem.setQuantity(orderedItem.getQuantity() + orderedItem.getQuantity());
                    return;
                }
            } else if (orderedItem.getName().equals(orderedItem.getName())) {
                orderedItem.setQuantity(orderedItem.getQuantity() + orderedItem.getQuantity());
                return;
            }
        }
        orderedItems.add(item);
    }
}
