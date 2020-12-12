package com.aiecel.gubernskyprintingdvor.notification;

import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.model.VkUser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@Component
@AllArgsConstructor
public class VkNotificator implements Notificator {
    private final VkApiClient vkApiClient;
    private final GroupActor actor;

    @Override
    public void sendNotification(Notification notification, Collection<User> recipients) {
        for (User recipient : recipients) {
            try {
                //only for vk users
                if (recipient instanceof VkUser) {
                    VkUser vkUser = (VkUser) recipient;

                    vkApiClient.messages().send(actor)
                            .userId(vkUser.getVkId())
                            .randomId(new Random().nextInt(Integer.MAX_VALUE))
                            .message(notification.getText())
                            .execute();
                }
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
                //todo exceptions
            }
        }
    }
}
