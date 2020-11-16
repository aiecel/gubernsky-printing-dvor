package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.vk.api.sdk.objects.messages.Message;

public abstract class VkMessageHandler implements MessageHandler<Message> {
    protected VkBotResponse constructResponse(String text) {
        return new VkBotResponse(new Message().setText(text), null); //todo
    }

    protected VkBotResponse constructResponse(String text, VkMessageHandler newMessageHandler) {
        return new VkBotResponse(new Message().setText(text), newMessageHandler); //todo
    }
}
