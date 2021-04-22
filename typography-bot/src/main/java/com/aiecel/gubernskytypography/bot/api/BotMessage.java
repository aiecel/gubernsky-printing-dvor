package com.aiecel.gubernskytypography.bot.api;

import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BotMessage {
    private final String text;
    private Keyboard keyboard;

    public boolean hasKeyboard() {
        return keyboard != null;
    }
}
