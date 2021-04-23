package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.service.FeedbackService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FeedbackMessageHandler extends AbstractMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Имеити благосовѣтіе для Двора Печатнаго?\n" +
                    "Огорчалися чѣмъ то,\n" +
                    "али шаленьство настигло?\n" +
                    "Дакъ не робѣй, да пиши же намъ поскорѣй!";

    public static final String MESSAGE_CONFIRM_SENDING =
            "Предпослати всё это мездникамъ двора?\nКоли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";

    public static final String MESSAGE_CANCEL =
            "Ну, такъ тому и бывати!\nЕжели надобность имѣется что болѣ сдѣлати во Дворѣ, " +
                    "благопослушливо готовы помогати въ дѣлахъ вашихъ";

    public static final String MESSAGE_FEEDBACK_SENT = "Жалоба отослана!\n" +
            "Ежели надобность имѣется что болѣ сдѣлати во Дворѣ, благопослушливо готовы помогати въ дѣлахъ вашихъ";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Пока не отсылати";
    public static final String ACTION_SEND = "\uD83D\uDC49\uD83C\uDFFB Послати!";

    private final FeedbackService feedbackService;
    private final Map<User, StringBuilder> feedbackBuilders;

    private final Keyboard noFeedbackKeyboard;
    private final Keyboard hasFeedbackKeyboard;

    @Setter(onMethod_ = @Autowired) //to avoid circular dependency
    private HomeMessageHandler homeMessageHandler;

    public FeedbackMessageHandler(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.feedbackBuilders = new HashMap<>();

        this.noFeedbackKeyboard = new KeyboardBuilder()
                .add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE))
                .build();

        this.hasFeedbackKeyboard = new KeyboardBuilder()
                .add(new Button(ACTION_SEND, ButtonType.POSITIVE))
                .add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE))
                .build();
    }

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return new BotMessage(DEFAULT_MESSAGE, noFeedbackKeyboard);
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        //cancel feedback
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        //get feedback builder
        StringBuilder feedbackBuilder = new StringBuilder();
        if (feedbackBuilders.containsKey(message.getUser())) {
            feedbackBuilder = feedbackBuilders.get(message.getUser());
        }

        //send feedback
        if (message.getText().equals(ACTION_SEND) && feedbackBuilder.length() > 0) {
            return onActionSend(chat, feedbackBuilder.toString());
        }

        //append text
        if (feedbackBuilder.length() > 0) {
            feedbackBuilder.append("; ");
        }
        feedbackBuilder.append(message.getText());
        feedbackBuilders.put(message.getUser(), feedbackBuilder);

        return new BotMessage(
                "\"" + feedbackBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM_SENDING,
                hasFeedbackKeyboard
        );
    }

    private BotMessage onActionCancel(Chat chat) {
        feedbackBuilders.remove(chat.getUser());
        chat.setMessageHandler(homeMessageHandler);
        return new BotMessage(MESSAGE_CANCEL, homeMessageHandler.getKeyboard());
    }

    private BotMessage onActionSend(Chat chat, String feedback) {
        feedbackService.send(feedback);
        chat.setMessageHandler(homeMessageHandler);
        return new BotMessage(MESSAGE_FEEDBACK_SENT, homeMessageHandler.getKeyboard());
    }
}
