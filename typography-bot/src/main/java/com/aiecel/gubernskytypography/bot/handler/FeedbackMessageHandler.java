package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.service.FeedbackService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
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
    private final StringBuilder feedbackBuilder;

    private final Keyboard noFeedbackKeyboard;
    private final Keyboard hasFeedbackKeyboard;

    @Setter(onMethod_ = @Autowired) //to avoid circular dependency
    private HomeMessageHandler homeMessageHandler;

    public FeedbackMessageHandler(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.feedbackBuilder = new StringBuilder();

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
    @SuppressWarnings("DuplicatedCode")
    public BotMessage onMessage(UserMessage message, Chat chat) {
        //cancel feedback
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        //send feedback
        if (message.getText().equals(ACTION_SEND) && feedbackBuilder.length() > 0) {
            return onActionSend(feedbackBuilder.toString(), chat);
        }

        //append text
        if (feedbackBuilder.length() > 0) {
            feedbackBuilder.append("; ");
        }
        feedbackBuilder.append(message.getText());

        return new BotMessage(
                "\"" + feedbackBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM_SENDING,
                hasFeedbackKeyboard
        );
    }

    private BotMessage onActionCancel(Chat chat) {
        log.info("Redirecting user {} to HomeMessageHandler", chat.getUser());
        chat.setMessageHandler(homeMessageHandler);
        return new BotMessage(MESSAGE_CANCEL, homeMessageHandler.getKeyboard());
    }

    private BotMessage onActionSend(String feedback, Chat chat) {
        feedbackService.send(feedback);

        log.info("Redirecting user {} to HomeMessageHandler", chat.getUser());
        chat.setMessageHandler(homeMessageHandler);
        return new BotMessage(MESSAGE_FEEDBACK_SENT, homeMessageHandler.getKeyboard());
    }
}
