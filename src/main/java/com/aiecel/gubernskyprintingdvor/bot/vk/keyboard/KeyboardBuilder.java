package com.aiecel.gubernskyprintingdvor.bot.vk.keyboard;

import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;

public interface KeyboardBuilder {
    KeyboardBuilder add(KeyboardButton button);

    KeyboardBuilder add(KeyboardButton button, int row, int column);

    Keyboard build();
}
