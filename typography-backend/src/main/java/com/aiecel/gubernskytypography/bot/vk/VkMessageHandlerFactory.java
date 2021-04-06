package com.aiecel.gubernskytypography.bot.vk;

import com.aiecel.gubernskytypography.bot.MessageHandler;
import com.aiecel.gubernskytypography.bot.MessageHandlerFactory;
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
