package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderedDocumentDTO.class, name = "document"),
        @JsonSubTypes.Type(value = OrderedProductDTO.class, name = "product")
})
public abstract class OrderedItemDTO {
    @Min(1)
    private final int quantity;

    public OrderedItemDTO(int quantity) {
        this.quantity = quantity;
    }
}
