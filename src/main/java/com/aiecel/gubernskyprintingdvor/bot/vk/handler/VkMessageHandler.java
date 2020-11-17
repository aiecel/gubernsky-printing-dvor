package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;

public abstract class VkMessageHandler extends MessageHandler<Message> {
    protected Message constructVkMessage(String text, Keyboard keyboard) {
        return new Message().setText(text).setKeyboard(keyboard);
    }

    protected Message constructVkMessage(String text) {
        return new Message().setText(text);
    }
}
