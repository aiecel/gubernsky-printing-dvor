package com.aiecel.gubernskytypography.bot.api.exception;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {
        super();
    }

    public ChatNotFoundException(String message) {
        super(message);
    }
}
