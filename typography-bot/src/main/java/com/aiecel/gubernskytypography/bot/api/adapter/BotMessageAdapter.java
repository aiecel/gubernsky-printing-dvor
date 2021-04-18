package com.aiecel.gubernskytypography.bot.api.adapter;

import com.aiecel.gubernskytypography.bot.api.BotMessage;

public interface BotMessageAdapter<M> {
    M fromBotMessage(BotMessage message);
}
