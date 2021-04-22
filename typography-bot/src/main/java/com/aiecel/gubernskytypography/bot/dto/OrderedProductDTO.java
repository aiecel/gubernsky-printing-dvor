package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class OrderedProductDTO extends OrderedItemDTO {
    @NotNull
    @Valid
    ProductDTO product;

    @JsonCreator
    public OrderedProductDTO(@JsonProperty("product") ProductDTO product,
                             @JsonProperty("quantity") int quantity) {
        super(quantity);
        this.product = product;
    }
}
