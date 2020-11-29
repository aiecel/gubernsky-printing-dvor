package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;

public interface MessageHandlerFactory<M> {
    <T extends MessageHandler<M>> T get(Class<T> clazz);
}
