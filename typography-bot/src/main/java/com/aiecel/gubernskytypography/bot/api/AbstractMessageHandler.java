package com.aiecel.gubernskytypography.bot.api;

public abstract class AbstractMessageHandler {
    public abstract BotMessage getDefaultResponse(Chat chat);
    public abstract BotMessage onMessage(UserMessage message, Chat chat);
}
