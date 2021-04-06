package com.aiecel.gubernskytypography.bot.vk.handler;

import com.aiecel.gubernskytypography.bot.Chatter;
import com.aiecel.gubernskytypography.bot.vk.keyboard.KeyboardBuilder;
import com.vk.api.sdk.objects.messages.*;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Setter
public class CommentOrderVkMessageHandler extends OrderDependedVkMessageHandler {
    public static final String DEFAULT_MESSAGE = "Напишите комментарий к заказу ежели хотити что-то передать мездникам двора";

    public static final String MESSAGE_CONFIRM = "Прикрепить эти слова к заказу?\nКоли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить";
    public static final String ACTION_CONFIRM = "✏ Прикрепить";

    private final StringBuilder commentTextBuilder;

    public CommentOrderVkMessageHandler() {
        this.commentTextBuilder = new StringBuilder();
    }

    @Override
    public Message getDefaultMessage() {
        if (commentTextBuilder.length() > 0) {
            return constructVkMessage(
                    "\"" + commentTextBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM,
                    keyboard()
            );
        } else {
            return constructVkMessage(DEFAULT_MESSAGE, keyboard());
        }
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //back to order handler
        if (message.getText().equals(ACTION_CANCEL)) {
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        }

        if (message.getText().equals(ACTION_CONFIRM) && commentTextBuilder.length() > 0) {
            //confirm comment
            getOrder().setComment(commentTextBuilder.toString());
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        } else {
            //append text
            if (commentTextBuilder.length() > 0) commentTextBuilder.append("; ");
            commentTextBuilder.append(message.getText());
            return getDefaultMessage();
        }
    }

    private Keyboard keyboard() {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        if (commentTextBuilder.length() > 0) {
            keyboardBuilder.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction()
                            .setLabel(ACTION_CONFIRM)
                            .setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.POSITIVE), 0, 0);
        }

        keyboardBuilder.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(ACTION_CANCEL)
                        .setType(KeyboardButtonActionType.TEXT))
                .setColor(KeyboardButtonColor.NEGATIVE), 0, 1);

        return keyboardBuilder.build();
    }
}
