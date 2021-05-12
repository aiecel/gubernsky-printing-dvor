package com.aiecel.gubernskytypography.bot.api;

import lombok.Data;

@Data
public class UserMessage {
    private final User user;
    private final String text;
    private final String source; //todo lol change it

    @Override
    public String toString() {
        return user + " - " + text;
    }
}
