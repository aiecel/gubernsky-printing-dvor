package com.aiecel.gubernskyprintingdvor.service.implementation;

import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.notification.Notification;
import com.aiecel.gubernskyprintingdvor.notification.Notificator;
import com.aiecel.gubernskyprintingdvor.service.NotificationService;
import com.aiecel.gubernskyprintingdvor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserService userService;

    private Collection<Notificator> notificators;

    @Autowired(required = false)
    public void setNotificators(Collection<Notificator> notificators) {
        this.notificators = notificators;
    }

    @Override
    public void sendNotification(Notification notification, Collection<User> recipients) {
        //send notifications in different thread
        Thread notificationThread = new Thread(() ->
                notificators.forEach(notificator -> notificator.sendNotification(notification, recipients))
        );
        notificationThread.start();
    }

    @Override
    public void sendNotificationToAdmins(Notification notification) {
        sendNotification(notification, userService.getAdmins());
    }
}
