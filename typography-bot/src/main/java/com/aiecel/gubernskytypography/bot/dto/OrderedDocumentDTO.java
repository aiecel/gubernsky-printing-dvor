package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class OrderedDocumentDTO extends OrderedItemDTO {
    @NotNull
    @Valid
    DocumentDTO document;

    @JsonCreator
    public OrderedDocumentDTO(@JsonProperty("document") DocumentDTO document,
                              @JsonProperty("quantity") int quantity) {
        super(quantity);
        this.document = document;
    }
}
