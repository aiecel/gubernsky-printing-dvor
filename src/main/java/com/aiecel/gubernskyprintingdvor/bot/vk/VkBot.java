package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Component
@Slf4j
public class VkBot {
    private final VkApiClient vkApiClient;
    private final VkChatter chatter;
    private final GroupActor actor;
    private final Random random;

    private volatile int lastTickTimestamp;

    @Autowired
    public VkBot(VkApiClient vkApiClient, VkChatter chatter, GroupActor actor) {
        this.vkApiClient = vkApiClient;
        this.chatter = chatter;
        this.actor = actor;
        this.random = new Random();
    }
    
    @PostConstruct
    public void start() {
        try {
            //init timestamp
            lastTickTimestamp = vkApiClient.messages().getLongPollServer(actor).execute().getTs();

            //schedule ticks
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    onTick();
                }
            }, 0, 2000);

            log.info("VK Bot started: GroupID {}, Access token: {}", actor.getGroupId(), actor.getAccessToken());
        } catch (ApiException | ClientException e) {
            log.error("Unable to start bot: {}", e.getMessage());
        }
    }

    private void onTick() {
        try {
            //get new messages since last tick
            List<Message> messages = vkApiClient.messages()
                    .getLongPollHistory(actor)
                    .ts(lastTickTimestamp)
                    .execute()
                    .getMessages()
                    .getItems();

            messages.forEach(userMessage -> {
                //check if the message was not from the bot
                if (!userMessage.isOut()) {
                    try {
                        sendMessage(userMessage.getFromId(), chatter.getAnswer(userMessage));
                    } catch (ClientException | ApiException e) {
                        log.error("Unable to handle message {}: {}", userMessage.getText(), e.getMessage());
                    }
                }
            });

            //update last tick timestamp
            lastTickTimestamp = vkApiClient.messages().getLongPollServer(actor).execute().getTs();
        } catch (ApiException | ClientException e) {
            log.error("VK Bot error: {}", e.getMessage());
        }
    }

    private void sendMessage(int toUserId, Message message) throws ClientException, ApiException {
        MessagesSendQuery sendQuery = vkApiClient.messages().send(actor);
        sendQuery
                .userId(toUserId)
                .randomId(random.nextInt(Integer.MAX_VALUE));

        //text
        sendQuery.message(message.getText());

        //keyboard
        if (message.getKeyboard() != null) {
            sendQuery.keyboard(message.getKeyboard());
        }

        //execute query
        sendQuery.execute();
    }
}
