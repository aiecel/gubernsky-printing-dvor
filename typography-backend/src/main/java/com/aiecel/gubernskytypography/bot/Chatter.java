package com.aiecel.gubernskytypography.bot;

public interface Chatter<M> {
    MessageHandler<M> getDefaultMessageHandler();
    void setMessageHandler(Integer userId, MessageHandler<M> messageHandler);
    M getAnswer(M message);
}
