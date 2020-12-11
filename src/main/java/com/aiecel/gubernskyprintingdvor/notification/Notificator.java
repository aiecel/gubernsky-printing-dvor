package com.aiecel.gubernskyprintingdvor.notification;

import com.aiecel.gubernskyprintingdvor.model.User;

import java.util.Collection;

public interface Notificator {
    void sendNotification(Notification notification, Collection<User> recipients);
}
