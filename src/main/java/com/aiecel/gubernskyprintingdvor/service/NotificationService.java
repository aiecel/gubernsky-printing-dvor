package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.notification.Notification;

import java.util.Collection;

public interface NotificationService {
    void sendNotification(Notification notification, Collection<User> recipients);
}
