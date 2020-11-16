package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.BotResponse;
import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
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
            messageHandlers.put(message.getFromId(), new HomeVkMessageHandler());
            vkUserService.registerVkUser(message.getFromId());
        }

        BotResponse<Message> response = messageHandlers.get(message.getFromId()).getResponse(message);

        if (response.hasNewMessageHandler()) {
            messageHandlers.put(message.getFromId(), response.getNewMessageHandler());
        }

        return response.getMessage();
    }
}
