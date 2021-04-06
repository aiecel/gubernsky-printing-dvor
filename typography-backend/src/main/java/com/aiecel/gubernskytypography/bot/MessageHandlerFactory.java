package com.aiecel.gubernskytypography.bot;

public interface MessageHandlerFactory<M> {
    <T extends MessageHandler<M>> T get(Class<T> clazz);
}
