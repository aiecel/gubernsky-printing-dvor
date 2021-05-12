package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.api.Bot;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.User;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

@RequiredArgsConstructor
@Slf4j
public abstract class LongPollingBotService extends Bot {
    public static final int TICK_PERIOD_MS = 2000;

    protected final UserService userService;

    public void start() {
        //schedule polling
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                poll();
            }
        }, 0, TICK_PERIOD_MS);
    }

    public void poll() {
        for (UserMessage message : fetchUnreadMessages()) {
            processMessage(message);
        }
    }

    protected void processMessage(UserMessage message) {
        try {
            User user = loadUser(message.getUser());
            UserMessage userMessage = new UserMessage(user, message.getText(), message.getSource());
            log.info("Received message: {}", userMessage);
            sendResponse(user, getResponse(userMessage));
        } catch (RuntimeException e) {
            log.error("Unable to process message {}: {}", message, e.getMessage());
        }
    }

    protected OffSiteUser loadUser(User user) {
        return userService
                .get(user.getUsername(), user.getProvider())
                .orElseGet(() -> userService.register(loadUserInfo(user)));
    }

    protected abstract Collection<UserMessage> fetchUnreadMessages();

    protected abstract OffSiteUser loadUserInfo(User user);

    protected abstract void sendResponse(User user, BotMessage botMessage);
}
