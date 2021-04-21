package com.aiecel.gubernskytypography.bot.api;

import com.aiecel.gubernskytypography.bot.api.exception.ChatNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Getter
public abstract class Bot {
    private final Set<Chat> chats = new HashSet<>();

    public abstract AbstractMessageHandler getDefaultMessageHandler();

    public BotMessage getResponse(UserMessage message) {
        try {
            Chat chat = findChat(message.getUser());
            return chat.getMessageHandler().onMessage(message, chat);
        } catch (ChatNotFoundException e) {
            Chat newChat = new Chat(message.getUser(), getDefaultMessageHandler());
            chats.add(newChat);
            return newChat.getMessageHandler().onMessage(message, newChat);
        }
    }

    private Chat findChat(User user) {
        return chats.stream()
                .filter(chat -> chat.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new ChatNotFoundException("Chat with user " + user + " not found"));
    }
}
