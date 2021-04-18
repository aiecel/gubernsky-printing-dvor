package com.aiecel.gubernskytypography.bot.api.keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Button {
    private final String text;
    private final ButtonType type;

    public Button(String text) {
        this.text = text;
        this.type = ButtonType.DEFAULT;
    }
}
