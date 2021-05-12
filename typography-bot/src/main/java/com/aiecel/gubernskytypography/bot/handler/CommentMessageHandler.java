package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.service.CartService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class CommentMessageHandler extends AbstractMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Напишите комментарий к заказу ежели хотити что-то передать мездникам двора";

    public static final String MESSAGE_CONFIRM =
            "Прикрепить эти слова к заказу?\nКоли настигло хотѣніе къ данному преболе добавить - дакъ пишите же!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Назадъ";
    public static final String ACTION_ATTACH = "✏ Прикрепить";

    private final StringBuilder commentBuilder;
    private final Keyboard noCommentKeyboard;
    private final Keyboard hasCommentKeyboard;

    @Setter(onMethod_ = @Autowired)
    private CartService cartService;

    @Setter(onMethod_ = @Autowired)
    private CartMessageHandler cartMessageHandler;

    @Setter
    private Cart cart;

    public CommentMessageHandler() {
        this.commentBuilder = new StringBuilder();

        this.noCommentKeyboard = new KeyboardBuilder()
                .add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE))
                .build();

        this.hasCommentKeyboard = new KeyboardBuilder()
                .add(new Button(ACTION_ATTACH, ButtonType.POSITIVE))
                .add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE))
                .build();
    }

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return new BotMessage(DEFAULT_MESSAGE, noCommentKeyboard);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public BotMessage onMessage(UserMessage message, Chat chat) {
        //cancel feedback
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        //attach comment
        if (message.getText().equals(ACTION_ATTACH) && commentBuilder.length() > 0) {
            return onActionAttach(chat);
        }

        //append text
        if (commentBuilder.length() > 0) {
            commentBuilder.append("; ");
        }
        commentBuilder.append(message.getText());

        return new BotMessage(
                "\"" + commentBuilder.toString() + "\"\n\n" + MESSAGE_CONFIRM,
                hasCommentKeyboard
        );
    }

    private BotMessage onActionCancel(Chat chat) {
        log.info("Redirecting user {} to CartMessageHandler", chat.getUser());
        chat.setMessageHandler(cartMessageHandler);
        return cartMessageHandler.getDefaultResponse(chat);
    }

    private BotMessage onActionAttach(Chat chat) {
        cart.setComment(commentBuilder.toString());
        cartService.save(cart);

        log.info("Redirecting user {} to CartMessageHandler", chat.getUser());
        chat.setMessageHandler(cartMessageHandler);
        return cartMessageHandler.getDefaultResponse(chat);
    }
}
