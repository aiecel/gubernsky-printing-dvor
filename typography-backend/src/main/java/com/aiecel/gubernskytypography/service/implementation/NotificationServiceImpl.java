package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.Role;
import com.aiecel.gubernskytypography.model.User;
import com.aiecel.gubernskytypography.notification.Notification;
import com.aiecel.gubernskytypography.notification.Notificator;
import com.aiecel.gubernskytypography.service.NotificationService;
import com.aiecel.gubernskytypography.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
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
        log.info("Sending {} to {} user(s)", notification.toString(), recipients.size());

        //send notifications in different thread
        Thread notificationThread = new Thread(() ->
                notificators.forEach(notificator -> notificator.sendNotification(notification, recipients))
        );
        notificationThread.start();
    }

    @Override
    public void sendNotificationToUsersWithRole(Notification notification, Role role) {
        Collection<User> recipients = userService.getUsersWithRole(role);
        sendNotification(notification, recipients);
    }
}
