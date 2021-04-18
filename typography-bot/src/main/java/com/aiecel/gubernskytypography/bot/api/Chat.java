package com.aiecel.gubernskytypography.bot.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Chat {
    private final User user;

    @Setter
    private AbstractMessageHandler messageHandler;
}
