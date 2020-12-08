package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.bot.MessageHandlerFactory;
import com.vk.api.sdk.objects.messages.Message;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VkMessageHandlerFactory implements MessageHandlerFactory<Message> {
    private final ApplicationContext applicationContext;

    @Override
    public <T extends MessageHandler<Message>> T get(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
