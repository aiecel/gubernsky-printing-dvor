package com.aiecel.gubernskytypography.bot.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String customerId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<CartItem> items;

    private String comment;
}
