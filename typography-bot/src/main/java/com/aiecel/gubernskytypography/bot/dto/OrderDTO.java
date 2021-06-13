package com.aiecel.gubernskytypography.bot.dto;

import com.aiecel.gubernskytypography.bot.model.OrderStatus;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;

@Value
public class OrderDTO {
    Collection<OrderedItemDTO> items;
    String comment;
    ZonedDateTime orderDateTime;
    OrderStatus status;
    BigDecimal price;
    boolean isPaid;
}
