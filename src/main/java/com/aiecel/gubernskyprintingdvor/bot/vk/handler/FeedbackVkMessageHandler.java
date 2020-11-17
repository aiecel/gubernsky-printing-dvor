package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.service.FeedbackService;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class FeedbackVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Есть предложения по улучшению Печатного Двора?\n" +
                    "Не понравилось обслуживание?\n" +
                    "Просто хотите написать гадостей?\n\n" +
                    "Напишите же сюда ваши думы!";

    public static final String MESSAGE_CONFIRM_SENDING = "Отправить всё это мездникам двора? Если хотiте что-то ещё добавить - дак пишите же!";
    public static final String MESSAGE_CANCEL_SENDING = "Если же захотите впредь что-то написать намъ - вы знаете как!";
    public static final String MESSAGE_FEEDBACK_SENT = "Жалоба отправлена! Благодаримъ васъ за ваше мненiе";

    public static final String ACTION_BACK_TO_HOME_HANDLER = "Не буду жаловаться";
    public static final String ACTION_CONFIRM_SENDING = "Отправить!";

    private final FeedbackService feedbackService;
    private final StringBuilder feedbackTextBuilder;

    public FeedbackVkMessageHandler(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.feedbackTextBuilder = new StringBuilder();
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //back to home handler
        if (message.getText().equals(ACTION_BACK_TO_HOME_HANDLER)) {
            chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
            return constructVkMessage(MESSAGE_CANCEL_SENDING, HomeVkMessageHandler.keyboard());
        }

        if (message.getText().equals(ACTION_CONFIRM_SENDING)) {
            //confirm sending
            feedbackService.save(feedbackTextBuilder.toString());
            chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
            return constructVkMessage(MESSAGE_FEEDBACK_SENT, HomeVkMessageHandler.keyboard());
        } else {
            //append text
            if (feedbackTextBuilder.length() > 0) feedbackTextBuilder.append("; ");
            feedbackTextBuilder.append(message.getText());
            return constructVkMessage(
                    "\"" + feedbackTextBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM_SENDING,
                    confirmSendingKeyboard()
            );
        }
    }

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
    }

    public static Keyboard toHomeHandlerKeyboard() {
        Keyboard keyboard = new Keyboard();

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_BACK_TO_HOME_HANDLER)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.PRIMARY)
        );

        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row1);

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
    }

    public static Keyboard confirmSendingKeyboard() {
        Keyboard keyboard = new Keyboard();

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_CONFIRM_SENDING)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.POSITIVE)
        );

        List<KeyboardButton> row2 = new ArrayList<>();
        row2.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_BACK_TO_HOME_HANDLER)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.NEGATIVE)
        );

        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row1);
        buttons.add(row2);

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
    }
}
