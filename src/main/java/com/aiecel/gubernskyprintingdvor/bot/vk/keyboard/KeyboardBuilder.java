package com.aiecel.gubernskyprintingdvor.bot.vk.keyboard;

public interface KeyboardBuilder<B, K> {
    KeyboardBuilder<B, K> add(B button);

    KeyboardBuilder<B, K> add(B button, int row, int column);

    K build();
}
