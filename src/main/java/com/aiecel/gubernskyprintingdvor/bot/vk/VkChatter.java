package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandlerFactory;
import com.aiecel.gubernskyprintingdvor.bot.vk.handler.HomeVkMessageHandler;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.messages.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class VkChatter implements Chatter<Message> {
    private final MessageHandlerFactory<Message> messageHandlerFactory;
    private final VkUserService vkUserService;
    private final Map<Integer, MessageHandler<Message>> messageHandlers;

    @Override
    public Message getAnswer(Message message) {
        if (!messageHandlers.containsKey(message.getFromId())) {
            setMessageHandler(message.getFromId(), getDefaultMessageHandler());
            if (!vkUserService.isUserExists(message.getFromId())) {
                vkUserService.register(message.getFromId());
            }
        }
        return messageHandlers.get(message.getFromId()).onMessage(message, this);
    }

    @Override
    public void setMessageHandler(Integer user, MessageHandler<Message> messageHandler) {
        messageHandlers.put(user, messageHandler);
    }

    @Override
    public MessageHandler<Message> getDefaultMessageHandler() {
        return messageHandlerFactory.get(HomeVkMessageHandler.class);
    }
}
