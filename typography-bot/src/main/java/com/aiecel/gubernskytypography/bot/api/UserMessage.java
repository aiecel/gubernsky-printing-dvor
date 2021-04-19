package com.aiecel.gubernskytypography.bot.api;

import lombok.Data;

@Data
public class UserMessage {
    private final User user;
    private final String text;
}
