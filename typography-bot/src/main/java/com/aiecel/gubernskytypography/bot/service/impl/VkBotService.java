package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.User;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.handler.HomeMessageHandler;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.service.LongPollingBotService;
import com.aiecel.gubernskytypography.bot.service.UserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VkBotService extends LongPollingBotService {
    public static final String PROVIDER_NAME = "vk";

    private final VkApiClient vkApiClient;
    private final GroupActor actor;
    private final Random random = new Random();

    private int lastTickTimestamp;

    private final HomeMessageHandler homeMessageHandler;

    public VkBotService(UserService userService,
                        VkApiClient vkApiClient,
                        GroupActor actor,
                        HomeMessageHandler homeMessageHandler) {
        super(userService);
        this.vkApiClient = vkApiClient;
        this.actor = actor;
        this.homeMessageHandler = homeMessageHandler;
    }

    @Override
    public AbstractMessageHandler getDefaultMessageHandler() {
        return homeMessageHandler;
    }

    @PostConstruct
    @Override
    public void start() {
        try {
            //init timestamp
            lastTickTimestamp = vkApiClient.messages().getLongPollServer(actor).execute().getTs();
            super.start();
            log.info("VK Bot started: GroupID {}, Access token: {}", actor.getGroupId(), actor.getAccessToken());
        } catch (ApiException | ClientException e) {
            log.error("Unable to start bot: {}", e.getMessage());
        }
    }

    @Override
    protected Collection<UserMessage> fetchUnreadMessages() {
        try {
            //get new messages since last tick
            List<Message> unreadMessages = vkApiClient.messages()
                    .getLongPollHistory(actor)
                    .ts(lastTickTimestamp)
                    .execute()
                    .getMessages()
                    .getItems()
                    .stream()
                    .filter(m -> !m.isOut())
                    .collect(Collectors.toList());

            //update last tick timestamp
            lastTickTimestamp = vkApiClient.messages().getLongPollServer(actor).execute().getTs();

            List<UserMessage> mappedMessages = new ArrayList<>();
            unreadMessages.forEach(message -> mappedMessages.add(convertMessage(message)));

            return mappedMessages;
        } catch (ApiException | ClientException e) {
            log.error("Unable to fetch unread messages: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @SneakyThrows
    protected OffSiteUser loadUserInfo(User user) {
        //get user data from VK API
        UserXtrCounters userXtrCounters = vkApiClient
                .users()
                .get(actor)
                .userIds(user.getUsername())
                .execute()
                .get(0);

        //construct user
        OffSiteUser offSiteUser = new OffSiteUser();
        offSiteUser.setUsername(user.getUsername());
        offSiteUser.setDisplayName(userXtrCounters.getFirstName() + " " + userXtrCounters.getLastName());
        offSiteUser.setProvider(PROVIDER_NAME);

        log.info("User info loaded: {}", offSiteUser);

        return offSiteUser;
    }

    @Override
    protected void sendResponse(User user, BotMessage botMessage) {
        try {
            MessagesSendQuery sendQuery = vkApiClient.messages().send(actor);
            sendQuery
                    .userId(Integer.valueOf(user.getUsername()))
                    .randomId(random.nextInt(Integer.MAX_VALUE));

            //text
            sendQuery.message(botMessage.getText());

            //keyboard
            if (botMessage.hasKeyboard()) {
                sendQuery.keyboard(convertKeyboard(botMessage.getKeyboard()));
            }

            //execute query
            sendQuery.execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    private UserMessage convertMessage(Message vkMessage) {
        OffSiteUser user = new OffSiteUser();
        user.setUsername(String.valueOf(vkMessage.getFromId()));
        user.setProvider(PROVIDER_NAME);
        return new UserMessage(user, vkMessage.getText(), PROVIDER_NAME);
    }

    public Keyboard convertKeyboard(com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard keyboard) {
        List<List<KeyboardButton>> buttons = new ArrayList<>();

        for (List<Button> row : keyboard.getButtons()) {
            List<KeyboardButton> newRow = new ArrayList<>();
            for (Button button : row) {
                newRow.add(convertButton(button));
            }
            buttons.add(newRow);
        }

        return new com.vk.api.sdk.objects.messages.Keyboard()
                .setButtons(buttons)
                .setOneTime(true);
    }

    private KeyboardButton convertButton(Button button) {
        KeyboardButtonColor color;

        switch (button.getType()) {
            case PRIMARY:
                color = KeyboardButtonColor.PRIMARY;
                break;

            case POSITIVE:
                color = KeyboardButtonColor.POSITIVE;
                break;

            case NEGATIVE:
                color = KeyboardButtonColor.NEGATIVE;
                break;

            default:
                color = KeyboardButtonColor.DEFAULT;
                break;
        }

        return new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(button.getText())
                        .setType(KeyboardButtonActionType.TEXT)
                ).setColor(color);
    }
}
