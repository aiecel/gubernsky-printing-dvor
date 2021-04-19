package com.aiecel.gubernskytypography.bot.api;

import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BotMessage {
    private final User to;
    private final String text;
    private final Keyboard keyboard;

    public BotMessage(User to, String text) {
        this.to = to;
        this.text = text;
        this.keyboard = null;
    }

    public boolean hasKeyboard() {
        return keyboard != null;
    }
}
