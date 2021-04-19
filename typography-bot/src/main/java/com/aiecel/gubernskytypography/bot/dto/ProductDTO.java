package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private final String name;
    private final String description;
    private final BigDecimal price;

    @JsonCreator
    public ProductDTO(@JsonProperty("name") String name,
                      @JsonProperty("description") String description,
                      @JsonProperty("price") BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
