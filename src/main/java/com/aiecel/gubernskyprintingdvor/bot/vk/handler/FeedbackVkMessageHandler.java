package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.service.FeedbackService;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class FeedbackVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Имеити благосовѣтіе для Двора Печатнаго?\n" +
                    "Огорчалися чѣмъ то,\n" +
                    "али шаленьство настигло?\n" +
                    "Дакъ не робѣй, да пиши же намъ поскорѣй!";

    public static final String MESSAGE_CONFIRM_SENDING = "Предпослати всё это мездникамъ двора?\n Коли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";
    public static final String MESSAGE_CANCEL_SENDING = "Ну, такъ тому и бывати!/nЕжели надобность имѣется что болѣ сдѣлати во Дворѣ, благопослушливо готовы помогати въ дѣлахъ вашихъ";
    public static final String MESSAGE_FEEDBACK_SENT = "Жалоба отослана!\nЕжели надобность имѣется что болѣ сдѣлати во Дворѣ, благопослушливо готовы помогати въ дѣлахъ вашихъ";

    public static final String ACTION_BACK_TO_HOME_HANDLER = "Пока не отсылати";
    public static final String ACTION_CONFIRM_SENDING = "Послати!";

    private final FeedbackService feedbackService;
    private final StringBuilder feedbackTextBuilder;

    @Autowired
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

        if (message.getText().equals(ACTION_CONFIRM_SENDING) && feedbackTextBuilder.length() > 0) {
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
