package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
@Slf4j
public class HomeVkMessageHandler extends VkMessageHandler {
    private final int id;

    public HomeVkMessageHandler() {
        id = new Random().nextInt(100);
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        return vkMessage("response=" + id);
    }
}
