package com.aiecel.gubernskytypography.bot.api;

import lombok.Data;

import java.util.Collection;

@Data
public class UserMessage {
    private final User user;
    private final String text;
    private final String source; //todo lol change it
    private final Collection<?> attachments;

    @Override
    public String toString() {
        return user + " - " + text;
    }
}
