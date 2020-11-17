package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VkChatter implements Chatter<Message> {
    private final VkUserService vkUserService;
    private final Map<Integer, MessageHandler<Message>> messageHandlers;

    @Autowired
    public VkChatter(VkUserService vkUserService) {
        this.vkUserService = vkUserService;
        this.messageHandlers = new HashMap<>();
    }

    @Override
    public Message getAnswer(Message message) {
        if (!messageHandlers.containsKey(message.getFromId())) {
            setMessageHandler(message.getFromId(), getDefaultMessageHandler());
            if (!vkUserService.isVkUserExists(message.getFromId())) {
                vkUserService.registerVkUser(message.getFromId());
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
        return getHomeVkMessageHandler();
    }

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
    }
}
