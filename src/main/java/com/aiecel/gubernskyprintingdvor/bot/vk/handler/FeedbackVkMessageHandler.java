package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.vk.keyboard.KeyboardBuilder;
import com.aiecel.gubernskyprintingdvor.service.FeedbackService;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FeedbackVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Имеити благосовѣтіе для Двора Печатнаго?\n" +
                    "Огорчалися чѣмъ то,\n" +
                    "али шаленьство настигло?\n" +
                    "Дакъ не робѣй, да пиши же намъ поскорѣй!";

    public static final String MESSAGE_CONFIRM_SENDING = "Предпослати всё это мездникамъ двора?\nКоли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";
    public static final String MESSAGE_CANCEL_SENDING = "Ну, такъ тому и бывати!/nЕжели надобность имѣется что болѣ сдѣлати во Дворѣ, благопослушливо готовы помогати въ дѣлахъ вашихъ";
    public static final String MESSAGE_FEEDBACK_SENT = "Жалоба отослана!\nЕжели надобность имѣется что болѣ сдѣлати во Дворѣ, благопослушливо готовы помогати въ дѣлахъ вашихъ";

    public static final String ACTION_BACK_TO_HOME_HANDLER = "\uD83D\uDEAB Пока не отсылати";
    public static final String ACTION_CONFIRM_SENDING = "\uD83D\uDC49\uD83C\uDFFB Послати!";

    private final FeedbackService feedbackService;
    private final StringBuilder feedbackTextBuilder;

    public FeedbackVkMessageHandler(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.feedbackTextBuilder = new StringBuilder();
    }

    @Override
    public Message getDefaultMessage() {
        return constructVkMessage(DEFAULT_MESSAGE, keyboard());
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //back to home handler
        if (message.getText().equals(ACTION_BACK_TO_HOME_HANDLER)) {
            HomeVkMessageHandler homeVkMessageHandler = messageHandlerFactory().get(HomeVkMessageHandler.class);
            chatter.setMessageHandler(message.getFromId(), homeVkMessageHandler);
            return constructVkMessage(MESSAGE_CANCEL_SENDING, homeVkMessageHandler.keyboard());
        }

        if (message.getText().equals(ACTION_CONFIRM_SENDING) && feedbackTextBuilder.length() > 0) {
            //confirm sending
            feedbackService.save(feedbackTextBuilder.toString());
            HomeVkMessageHandler homeVkMessageHandler = messageHandlerFactory().get(HomeVkMessageHandler.class);
            chatter.setMessageHandler(message.getFromId(), homeVkMessageHandler);
            return constructVkMessage(MESSAGE_FEEDBACK_SENT, homeVkMessageHandler.keyboard());
        } else {
            //append text
            if (feedbackTextBuilder.length() > 0) feedbackTextBuilder.append("; ");
            feedbackTextBuilder.append(message.getText());
            return constructVkMessage(
                    "\"" + feedbackTextBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM_SENDING,
                    keyboard()
            );
        }
    }

    private Keyboard keyboard() {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        if (feedbackTextBuilder.length() > 0) {
            keyboardBuilder.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction()
                            .setLabel(ACTION_CONFIRM_SENDING)
                            .setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.POSITIVE), 0, 0);
        }

        keyboardBuilder.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(ACTION_BACK_TO_HOME_HANDLER)
                        .setType(KeyboardButtonActionType.TEXT))
                .setColor(KeyboardButtonColor.NEGATIVE), 0, 1);

        return keyboardBuilder.build();
    }
}
