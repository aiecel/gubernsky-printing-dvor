package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.Chat;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.model.Product;
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
public class AddProductMessageHandler extends AbstractMessageHandler {
    public static final int MAX_QUANTITY = 100;

    public static final String MESSAGE_ASK_QUANTITY = "Сколько пожелаете?";
    public static final String MESSAGE_ASK_QUANTITY_AGAIN = "Сколько ещё разъ?";
    public static final String MESSAGE_ZERO_QUANTITY = "Ну, ноль так ноль. Что-то ещё?"; //not used for now
    public static final String MESSAGE_TOO_MUCH_QUANTITY = "\uD83D\uDE33 Ух! Столь много не можемъ вамъ выдать! Давайте поменьше";
    public static final String MESSAGE_PRODUCT_PRICE = "\uD83D\uDE0F Сегодня у насъ %s стоитъ %s рублей!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Ладно, не буду";
    public static final String ACTION_CHECK_PRICE = "\uD83D\uDC40 А по чёмъ штука?";

    private final Keyboard keyboard;

    @Setter(onMethod_ = @Autowired)
    private CartMessageHandler cartMessageHandler;

    @Setter(onMethod_ = @Autowired)
    private CartService cartService;

    @Setter
    private Product product;

    public AddProductMessageHandler() {
        this.keyboard = new KeyboardBuilder()
                .add(new Button(ACTION_CHECK_PRICE))
                .add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE))
                .build();
    }

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return new BotMessage(product.getDescription() + "\n\n" + MESSAGE_ASK_QUANTITY, keyboard);
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        //cancel ordering product
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        //check price
        if (message.getText().equals(ACTION_CHECK_PRICE)) {
            return onActionCheckPrice();
        }

        //read the quantity
        try {
            int quantity = Integer.parseInt(message.getText());

            if (quantity < 0) throw new NumberFormatException();
            if (quantity == 0) return onActionCancel(chat);
            if (quantity > MAX_QUANTITY) return new BotMessage(MESSAGE_TOO_MUCH_QUANTITY, keyboard);

            return onActionOrderProduct(quantity, chat);
        } catch (NumberFormatException e) {
            return new BotMessage(MESSAGE_ASK_QUANTITY_AGAIN, keyboard);
        }
    }

    private BotMessage onActionOrderProduct(int quantity, Chat chat) {
        cartService.addProductToCart((OffSiteUser) chat.getUser(), product, quantity);
        return onActionCancel(chat);
    }

    private BotMessage onActionCheckPrice() {
        return new BotMessage(
                String.format(MESSAGE_PRODUCT_PRICE, product.getName().toLowerCase(), product.getPrice())
                        + "\n" + MESSAGE_ASK_QUANTITY,
                keyboard
        );
    }

    private BotMessage onActionCancel(Chat chat) {
        log.info("Redirecting user {} to CartMessageHandler", chat.getUser());
        chat.setMessageHandler(cartMessageHandler);
        return cartMessageHandler.getDefaultResponse(chat);
    }
}
