package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandlerFactory;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VkMessageHandler extends MessageHandler<Message> {
    private MessageHandlerFactory<Message> messageHandlerFactory;

    protected MessageHandlerFactory<Message> messageHandlerFactory() {
        return messageHandlerFactory;
    }

    @Autowired
    public final void setMessageHandlerFactory(MessageHandlerFactory<Message> messageHandlerFactory) {
        this.messageHandlerFactory = messageHandlerFactory;
    }

    protected Message constructVkMessage(String text, Keyboard keyboard) {
        return new Message().setText(text).setKeyboard(keyboard);
    }

    protected Message constructVkMessage(String text) {
        return new Message().setText(text);
    }

    protected Message proceedToNewMessageHandler(int vkId, MessageHandler<Message> newMessageHandler, Chatter<Message> chatter) {
        chatter.setMessageHandler(vkId, newMessageHandler);
        return newMessageHandler.getDefaultMessage();
    }
}
