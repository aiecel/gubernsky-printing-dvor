package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.User;
import com.aiecel.gubernskytypography.notification.Notification;

import java.util.Collection;

public interface NotificationService {
    void sendNotification(Notification notification, Collection<User> recipients);
    void sendNotificationToAdmins(Notification notification);
}
