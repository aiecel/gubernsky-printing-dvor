package com.aiecel.gubernskyprintingdvor.notification;

import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.model.VkUser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.responses.GetHistoryResponse;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
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
                if (recipient instanceof VkUser) {
                    VkUser vkUser = (VkUser) recipient;

                    MessagesSendQuery sendQuery = vkApiClient.messages().send(actor);
                    sendQuery
                            .userId(vkUser.getVkId())
                            .randomId(new Random().nextInt(Integer.MAX_VALUE));

                    //text
                    sendQuery.message(notification.getText());

                    //keyboard
                    Message lastMessage = lastMessageWithUser(vkUser.getVkId());
                    if (lastMessage != null
                            && lastMessage.isOut()
                            && lastMessage.getKeyboard() != null
                            && isKeyboardNotEmpty(lastMessage.getKeyboard())) {
                        Keyboard keyboard = new Keyboard();
                        keyboard.setButtons(lastMessage.getKeyboard().getButtons());
                        sendQuery.keyboard(keyboard);
                    }

                    //execute query
                    sendQuery.execute();
                }
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
                //todo exceptions
            }
        }
    }

    //todo get rid of whole method
    private Message lastMessageWithUser(Integer vkUserId) {
        try {
            GetHistoryResponse response = vkApiClient.messages().getHistory(actor).userId(vkUserId).count(1).execute();
            return response.getItems().size() > 0 ? response.getItems().get(0) : null;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isKeyboardNotEmpty(Keyboard keyboard) {
        if (keyboard.getButtons().size() == 0) return false;
        return keyboard.getButtons().stream().anyMatch(keyboardButtons -> !keyboardButtons.isEmpty());
    }
}
