package com.aiecel.gubernskytypography.notification;

import com.aiecel.gubernskytypography.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class NewOrderNotification implements Notification {
    public static final String MESSAGE = "Гой еси, новый заказъ в дворе!\nОт: %s\nСтоимость: %s";

    private final Order order;

    @Override
    public String getText() {
        String customer = order.getCustomer().getDisplayName();
        return String.format(MESSAGE, customer, order.getPrice() + " руб.");
    }

    @Override
    public String toString() {
        return "New Order Notification";
    }
}
