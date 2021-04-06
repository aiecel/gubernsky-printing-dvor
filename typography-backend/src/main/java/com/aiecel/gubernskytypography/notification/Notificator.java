package com.aiecel.gubernskytypography.notification;

import com.aiecel.gubernskytypography.model.User;

import java.util.Collection;

public interface Notificator {
    void sendNotification(Notification notification, Collection<User> recipients);
}
