package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.Bot;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.bot.handler.HomeMessageHandler;
import com.aiecel.gubernskytypography.bot.vk.VkSDKAdapter;
import com.aiecel.gubernskytypography.bot.vk.VkUser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
@Slf4j
public class VkBotService extends Bot {
    public static final int TICK_PERIOD_MS = 2000;

    private final VkApiClient vkApiClient;
    private final GroupActor actor;
    private final VkSDKAdapter vkSDKAdapter;
    private final Random random = new Random();

    private int lastTickTimestamp;

    private final HomeMessageHandler homeMessageHandler;

    @Override
    public AbstractMessageHandler getDefaultMessageHandler() {
        return homeMessageHandler;
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
            }, 0, TICK_PERIOD_MS);

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
                        processUserMessage(userMessage);
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

    private void processUserMessage(Message message) throws ClientException, ApiException {
        UserMessage userMessage = vkSDKAdapter.toUserMessage(message);

        if (getChats().stream().noneMatch(chat -> chat.getUser().equals(userMessage.getUser()))) {
            registerUser((VkUser) userMessage.getUser());
        }

        BotMessage botMessage = getResponse(userMessage);
        sendMessage(message.getFromId(), botMessage);
    }

    private void sendMessage(int userId, BotMessage botMessage) throws ClientException, ApiException {
        MessagesSendQuery sendQuery = vkApiClient.messages().send(actor);
        sendQuery
                .userId(userId)
                .randomId(random.nextInt(Integer.MAX_VALUE));

        //text
        sendQuery.message(botMessage.getText());

        //keyboard
        if (botMessage.hasKeyboard()) {
            sendQuery.keyboard(vkSDKAdapter.fromKeyboard(botMessage.getKeyboard()));
        }

        //execute query
        sendQuery.execute();
    }

    private void registerUser(VkUser vkUser) {
        try {
            //get user data from VK API
            UserXtrCounters userXtrCounters = vkApiClient
                    .users()
                    .get(actor)
                    .userIds(vkUser.getId())
                    .execute()
                    .get(0);

            //construct UserDTO
            OffSiteUserDTO userDTO = new OffSiteUserDTO(
                    vkUser.getId(),
                    userXtrCounters.getFirstName() + " " + userXtrCounters.getLastName(),
                    "vk"
            );

            //register user
            WebClient webClient = WebClient.create("http://localhost:8085");
            Mono<OffSiteUserDTO> userDTOMono = webClient
                    .post()
                    .uri("http://localhost:8080/offSiteUsers/register")
                    .body(Mono.just(userDTO), OffSiteUserDTO.class)
                    .retrieve()
                    .bodyToMono(OffSiteUserDTO.class);

            OffSiteUserDTO registeredUserDTO = userDTOMono.block();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
