package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HomeVkMessageHandler extends VkMessageHandler {
    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        return vkMessage("response");
    }
}
