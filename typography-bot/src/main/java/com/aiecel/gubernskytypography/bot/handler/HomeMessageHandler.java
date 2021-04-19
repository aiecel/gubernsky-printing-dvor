package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.Chat;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;

public class HomeMessageHandler extends AbstractMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Губернский Печатный Дворъ привѣтствуетъ тебя, засельщина земли Губернской! \n" +
                    "Возжелаешь ли отпечатать бумаги свои? Нашъ станъ готовъ неустанно трудиться денно и нощно";

    public static final String MESSAGE_ORDERS_LIST_TITLE = "\uD83D\uDCBC Вотъ все ваши заказы:";
    public static final String MESSAGE_ORDER_TITLE = "Заказ %s: %s, %s";
    public static final String MESSAGE_ORDER_PAID = "оплаченъ ✅";
    public static final String MESSAGE_ORDER_NOT_PAID = "НЕ ОПЛАЧЕНЪ ⚠";
    public static final String MESSAGE_ORDERED_DOCUMENT = "• Документъ \"%s\" - %d шт.";
    public static final String MESSAGE_ORDERED_PRODUCT = "• %s - %d шт.";
    public static final String MESSAGE_COMMENT = "✏ Комментарий к заказу: \"%s\"";
    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 Стоимость: %s руб.";

    public static final String ACTION_ORDER = "\uD83D\uDCE6 Сделать заказъ";
    public static final String ACTION_CHECK_ORDERS = "\uD83D\uDCBC Моя корреспонденция";
    public static final String ACTION_FEEDBACK = "\uD83D\uDCD6 Книга жалобъ";

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return new BotMessage(chat.getUser(), DEFAULT_MESSAGE, keyboard());
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        switch (message.getText()) {
            case ACTION_ORDER:
                return onActionOrder(chat);

            case ACTION_CHECK_ORDERS:
                return onActionCheckOrders(chat);

            case ACTION_FEEDBACK:
                return onActionFeedback(chat);
        }
        return getDefaultResponse(chat);
    }

    public BotMessage onActionOrder(Chat chat) {
        //todo change to order message handler
        AbstractMessageHandler messageHandler = new TestMessageHandler();
        chat.setMessageHandler(messageHandler);
        return messageHandler.getDefaultResponse(chat);
    }

    public BotMessage onActionCheckOrders(Chat chat) {
        //todo change behavior
        AbstractMessageHandler messageHandler = new TestMessageHandler();
        chat.setMessageHandler(messageHandler);
        return messageHandler.getDefaultResponse(chat);
    }

    private BotMessage onActionFeedback(Chat chat) {
        //todo change to feedback message handler
        AbstractMessageHandler messageHandler = new TestMessageHandler();
        chat.setMessageHandler(messageHandler);
        return messageHandler.getDefaultResponse(chat);
    }

    public Keyboard keyboard() {
        return new KeyboardBuilder()
                .add(new Button(ACTION_ORDER, ButtonType.PRIMARY))
                .add(new Button(ACTION_CHECK_ORDERS))
                .add(new Button(ACTION_FEEDBACK))
                .build();
    }
}
