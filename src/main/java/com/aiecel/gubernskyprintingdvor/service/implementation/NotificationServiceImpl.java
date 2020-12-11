package com.aiecel.gubernskyprintingdvor.service.implementation;

import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.notification.Notification;
import com.aiecel.gubernskyprintingdvor.notification.Notificator;
import com.aiecel.gubernskyprintingdvor.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NotificationServiceImpl implements NotificationService {
    private Collection<Notificator> notificators;

    @Autowired(required = false)
    public void setNotificators(Collection<Notificator> notificators) {
        this.notificators = notificators;
    }

    @Override
    public void sendNotification(Notification notification, Collection<User> recipients) {
        notificators.forEach(notificator -> notificator.sendNotification(notification, recipients));
    }
}
