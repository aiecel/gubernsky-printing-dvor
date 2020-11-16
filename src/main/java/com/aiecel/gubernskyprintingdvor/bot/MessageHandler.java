package com.aiecel.gubernskyprintingdvor.bot;

public interface MessageHandler<M> {
    BotResponse<M> getDefaultResponse();
    BotResponse<M> getResponse(M message);
}
