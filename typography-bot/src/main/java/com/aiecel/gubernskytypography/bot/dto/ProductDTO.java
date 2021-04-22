package com.aiecel.gubernskytypography.bot.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class ProductDTO {
    @NotBlank
    String name;

    String description;

    @NotNull
    BigDecimal price;
}
