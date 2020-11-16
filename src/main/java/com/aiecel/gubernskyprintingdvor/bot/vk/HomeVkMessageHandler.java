package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.BotResponse;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Component;

@Component
public class HomeVkMessageHandler extends VkMessageHandler {
    @Override
    public BotResponse<Message> getDefaultResponse() {
        return constructResponse("default");
    }

    @Override
    public BotResponse<Message> getResponse(Message message) {
        return constructResponse("response");
    }
}
