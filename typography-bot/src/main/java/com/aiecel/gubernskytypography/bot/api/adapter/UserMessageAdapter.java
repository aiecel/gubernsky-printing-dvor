package com.aiecel.gubernskytypography.bot.api.adapter;

import com.aiecel.gubernskytypography.bot.api.UserMessage;

public interface UserMessageAdapter<M> {
    UserMessage toUserMessage(M message);
}
