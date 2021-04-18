package com.aiecel.gubernskytypography.bot.api.keyboard;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Keyboard {
    private final List<List<Button>> buttons;

    public List<List<Button>> getButtons() {
        return buttons;
    }
}
