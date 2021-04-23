package com.aiecel.gubernskytypography.notification;

import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.SiteUser;
import com.aiecel.gubernskytypography.model.OffSiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class NewOrderNotification implements Notification {
    public static final String MESSAGE = "Гой еси, новый заказъ в дворе!\nОт: %s\nСтоимость: %s";

    private final Order order;

    @Override
    public String getText() {
        String customer = "Незнакомецъ";

        if (order.getCustomer() instanceof OffSiteUser) {
            OffSiteUser user = (OffSiteUser) order.getCustomer();
            customer = user.getDisplayName();
        } else if (order.getCustomer() instanceof SiteUser) {
            SiteUser user = (SiteUser) order.getCustomer();
            customer = user.getUsername();
        }

        return String.format(MESSAGE, customer, order.getPrice() + " руб.");
    }

    @Override
    public String toString() {
        return "New Order Notification";
    }
}
