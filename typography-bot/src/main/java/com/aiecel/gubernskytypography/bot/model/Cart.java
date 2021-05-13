package com.aiecel.gubernskytypography.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private OffSiteUser customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<CartItem> items = new ArrayList<>();

    private String comment = "";

    public void addItem(CartItem cartItem) {
        items.add(cartItem);
    }

    public void removeComment() {
        comment = "";
    }

    public String toPrettyString() {
        StringBuilder builder = new StringBuilder();
        if (items.size() > 0) {
            items.forEach(item -> {
                builder.append("• ").append(item).append("\n");
            });
        } else {
            builder.append("Корзина пуста");
        }

        //remove \n in the end
        builder.deleteCharAt(builder.length() - 1);

        if (comment.length() > 0) {
            builder.append("\n\n").append("Комментарий к заказу: '").append(comment).append("'");
        }

        return builder.toString();
    }
}
