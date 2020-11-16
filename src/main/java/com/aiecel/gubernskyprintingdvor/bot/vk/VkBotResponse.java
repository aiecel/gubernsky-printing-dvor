package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.BotResponse;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.vk.api.sdk.objects.messages.Message;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VkBotResponse implements BotResponse<Message> {
    private final Message message;
    private final VkMessageHandler newMessageHandler;

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean hasNewMessageHandler() {
        return newMessageHandler != null;
    }

    @Override
    public MessageHandler<Message> getNewMessageHandler() {
        return newMessageHandler;
    }
}
