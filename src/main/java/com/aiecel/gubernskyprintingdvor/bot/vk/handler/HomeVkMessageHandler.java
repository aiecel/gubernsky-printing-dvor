package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class HomeVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Губернский Печатный Дворъ привѣтствуетъ тебя, засельщина земли Губернской! \n" +
                    "Возжелаешь ли отпечатать бумаги свои? Нашъ станъ готовъ неустанно трудиться денно и нощно";

    public static final String MESSAGE_GREETINGS = "На добр приветъ добр и ответъ!";

    public static final String ACTION_ORDER = "Сделать заказъ";
    public static final String ACTION_FEEDBACK = "Книга жалобъ";

    @Override
    public Message getDefaultMessage() {
        return constructVkMessage(DEFAULT_MESSAGE, keyboard());
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //proceed to order handler
        if (message.getText().equals(ACTION_ORDER)) {
            OrderVkMessageHandler messageHandler = getOrderVkMessageHandler();
            messageHandler.setVkUserId(message.getFromId());
            return proceedToNewMessageHandler(message.getFromId(), messageHandler, chatter);
        }

        //proceed to feedback handler
        if (message.getText().equals(ACTION_FEEDBACK)) {
            return proceedToNewMessageHandler(message.getFromId(), getFeedbackVkMessageHandler(), chatter);
        }

        //greetings
        if (isMessageContainsGreetings(message.getText())) {
            return constructVkMessage(MESSAGE_GREETINGS, keyboard());
        }

        return constructVkMessage(DEFAULT_MESSAGE, keyboard());
    }

    public static Keyboard keyboard() {
        Keyboard keyboard = new Keyboard();

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_ORDER)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.PRIMARY)
        );

        List<KeyboardButton> row2 = new ArrayList<>();
        row2.add(
                new KeyboardButton()
                        .setAction(
                                new KeyboardButtonAction()
                                        .setLabel(ACTION_FEEDBACK)
                                        .setType(KeyboardButtonActionType.TEXT)
                        )
        );

        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row1);
        buttons.add(row2);

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
    }

    private boolean isMessageContainsGreetings(String message) {
        String messageLowerCase = message.toLowerCase();

        return messageLowerCase.contains("привет") ||
                messageLowerCase.contains("здарова") ||
                messageLowerCase.contains("здоров") ||
                messageLowerCase.contains("здравс") ||
                messageLowerCase.equals("ку");
    }

    @Lookup
    public OrderVkMessageHandler getOrderVkMessageHandler() {
        return null;
    }

    @Lookup
    public FeedbackVkMessageHandler getFeedbackVkMessageHandler() {
        return null;
    }
}
