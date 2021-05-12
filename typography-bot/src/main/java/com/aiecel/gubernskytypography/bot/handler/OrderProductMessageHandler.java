package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.Chat;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMessageHandler extends AbstractMessageHandler {
    public static final String MESSAGE_ASK_QUANTITY = "Сколько пожелаете?";
    public static final String MESSAGE_ASK_QUANTITY_AGAIN = "Сколько ещё разъ?";
    public static final String MESSAGE_ZERO_QUANTITY = "Ну, ноль так ноль. Что-то ещё?"; //not used for now
    public static final String MESSAGE_TOO_MUCH_QUANTITY = "\uD83D\uDE33 Ух! Столь много не можемъ вамъ выдать! Давайте поменьше";
    public static final String MESSAGE_PRODUCT_PRICE = "\uD83D\uDE0F Сегодня у насъ %s стоитъ %s рублей!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Ладно, не буду";
    public static final String ACTION_CHECK_PRICE = "\uD83D\uDC40 А по чёмъ штука?";

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return null;
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        return null;
    }
}
