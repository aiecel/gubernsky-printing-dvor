package com.aiecel.gubernskyprintingdvor.bot;

public interface MessageHandlerFactory<M> {
    <T extends MessageHandler<M>> T get(Class<T> clazz);
}
