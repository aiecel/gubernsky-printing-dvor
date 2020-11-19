package com.aiecel.gubernskyprintingdvor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String title;

    private byte[] data;

    private int pages;
}
