package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.vk.api.sdk.objects.messages.Message;

public abstract class VkMessageHandler extends MessageHandler<Message> {
    protected Message vkMessage(String text) {
        return new Message().setText(text);
    }
}
