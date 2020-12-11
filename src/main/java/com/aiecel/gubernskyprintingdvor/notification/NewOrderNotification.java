package com.aiecel.gubernskyprintingdvor.notification;

import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.SiteUser;
import com.aiecel.gubernskyprintingdvor.model.VkUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class NewOrderNotification implements Notification {
    public static final String MESSAGE = "Гой еси, новый заказъ в дворе!\nОт кого: %s\nСтоимость: %s";

    private final Order order;

    @Override
    public String getText() {
        String customer = "Неизвестно";

        if (order.getCustomer() instanceof VkUser) {
            VkUser user = (VkUser) order.getCustomer();
            customer = user.getFirstName() + " " + user.getLastName();
        } else if (order.getCustomer() instanceof SiteUser) {
            SiteUser user = (SiteUser) order.getCustomer();
            customer = user.getUsername();
        }

        return String.format(MESSAGE, customer, order.getPrice() + " руб.");
    }
}
