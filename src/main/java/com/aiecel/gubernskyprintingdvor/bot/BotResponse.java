package com.aiecel.gubernskyprintingdvor.bot;

public interface BotResponse<M> {
    M getMessage();
    boolean hasNewMessageHandler();
    MessageHandler<M> getNewMessageHandler();
}
