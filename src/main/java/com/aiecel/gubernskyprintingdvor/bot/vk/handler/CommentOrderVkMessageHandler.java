package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.vk.keyboard.VkKeyboardBuilder;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.vk.api.sdk.objects.messages.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Setter
public class CommentOrderVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE = "Напишите комментарий к заказу ежели хотити что-то передать мездникам двора";

    public static final String MESSAGE_CONFIRM = "Прикрепить эти слова к заказу?\nКоли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить";
    public static final String ACTION_CONFIRM = "✏ Прикрепить";

    private final StringBuilder commentTextBuilder;

    private Order order;

    public CommentOrderVkMessageHandler() {
        this.commentTextBuilder = new StringBuilder();
    }

    @Override
    public Message getDefaultMessage() {
        if (commentTextBuilder.length() > 0) {
            return constructVkMessage(
                    "\"" + commentTextBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM,
                    confirmCommentKeyboard()
            );
        } else {
            return constructVkMessage(DEFAULT_MESSAGE, toOrderHandlerKeyboard());
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
            order.setComment(commentTextBuilder.toString());
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        } else {
            //append text
            if (commentTextBuilder.length() > 0) commentTextBuilder.append("; ");
            commentTextBuilder.append(message.getText());
            return getDefaultMessage();
        }
    }

    public static Keyboard toOrderHandlerKeyboard() {
        return new VkKeyboardBuilder()
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_CANCEL)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.NEGATIVE))
                .build();
    }

    public static Keyboard confirmCommentKeyboard() {
        return new VkKeyboardBuilder()
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_CONFIRM)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.POSITIVE), 0, 0)
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_CANCEL)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.NEGATIVE), 0, 1)
                .build();
    }

    private Message proceedToOrderVkMessageHandler(int vkId, Chatter<Message> chatter) {
        OrderVkMessageHandler orderVkMessageHandler = getOrderVkMessageHandler();
        orderVkMessageHandler.setOrder(order);
        chatter.setMessageHandler(vkId, orderVkMessageHandler);
        return orderVkMessageHandler.getDefaultMessage();
    }

    @Lookup
    public OrderVkMessageHandler getOrderVkMessageHandler() {
        return null;
    }
}
