package com.aiecel.gubernskytypography.bot.api.adapter;

import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;

public interface KeyboardAdapter<K> {
    K fromKeyboard(Keyboard keyboard);
}
