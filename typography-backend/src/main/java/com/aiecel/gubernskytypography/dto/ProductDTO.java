package com.aiecel.gubernskytypography.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @NotNull
    private final BigDecimal price;
}
