package com.aiecel.gubernskytypography.bot.model;

public enum OrderStatus {
    PENDING("На рассмотрении"),
    APPROVED("Принятъ к печати"),
    PRINTED("Отпечатанъ"),
    REJECTED("Отвергнутъ"),
    CANCELLED("Отменёнъ");

    public static OrderStatus DEFAULT = PENDING;

    OrderStatus(String description) {
        this.description = description;
    }

    private final String description;

    public String getDescription() {
        return description;
    }
}
