package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.BotResponse;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
public class HomeVkMessageHandler extends VkMessageHandler {
    private final int randomInt;

    public HomeVkMessageHandler() {
        randomInt = new Random().nextInt(100);
    }

    @Override
    public BotResponse<Message> getResponse(Message message) {
        return constructResponse("response=" + randomInt);
    }
}
