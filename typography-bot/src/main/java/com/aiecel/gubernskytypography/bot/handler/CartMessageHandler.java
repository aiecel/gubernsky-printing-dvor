package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.AbstractMessageHandler;
import com.aiecel.gubernskytypography.bot.api.BotMessage;
import com.aiecel.gubernskytypography.bot.api.Chat;
import com.aiecel.gubernskytypography.bot.api.UserMessage;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CartMessageHandler extends AbstractMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "\uD83D\uDCF0 Хотите что-то отпечатать? Просто взять товару? Как вамъ угодно!";

    public static final String MESSAGE_CART = "\uD83D\uDED2 Ваша корзина:\n";
    public static final String MESSAGE_CART_DOCUMENT = "• Документъ \"%s\" - %d шт.";
    public static final String MESSAGE_CART_PRODUCT = "• %s - %d шт.";
    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 ИТОГО: %s руб.";
    public static final String MESSAGE_COMMENT = "✏ Комментарий к заказу: \"%s\"";
    public static final String MESSAGE_ON_CANCEL = "\uD83E\uDD25 Эх, ну ладно. Ежели что-то хотите сделать - только напишите!";
    public static final String MESSAGE_WHAT_ELSE = "Что-то ещё хотите? Не стесняйтесь, берите!";
    public static final String MESSAGE_SINGLE_DOCUMENT =
            "Пожалуйста, прикрепити эти документы раздельно, чтобы мы могли спросити количество копий для каждаго";

    public static final String MESSAGE_DOCUMENT_BUILD_ERROR = "Даже и не знаемъ, что сказати...\n" +
            "Произошла беда при обработке вашего документа. Обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_DOWNLOAD_ERROR =
            "Кажется, произошелъ обрыв телеграфного провода, ибо намъ не удаётся получить ваш документъ.\n" +
                    "Попробуйте позже, или, если проблема повторится, обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_NOT_SUPPORTED =
            "Мы видимъ документъ, да не узнаем почерк... \nТакой формат мы пока не воспринимаемъ";

    public static final String ACTION_TO_PAYMENT = "\uD83D\uDCB4 Оплатить!";
    public static final String ACTION_ATTACH_COMMENT = "✏ Прикрепить комментарий";
    public static final String ACTION_REMOVE_COMMENT = "✏ Убрать комментарий";
    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить заказъ";

    @Setter(onMethod_ = @Autowired) //to avoid circular dependency
    private HomeMessageHandler homeMessageHandler;

    private final CartService cartService;

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        Cart cart = getCart(chat);
        return new BotMessage(buildCartDescription(cart) + "\n\n" + MESSAGE_WHAT_ELSE, buildKeyboard(cart));
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        Cart cart = getCart(chat);
        //attach comment
        if (message.getText().equals(ACTION_ATTACH_COMMENT) && cart.getComment().length() == 0) {
            return onActionAttachComment(chat);
        }

        //remove comment
        if (message.getText().equals(ACTION_REMOVE_COMMENT) && cart.getComment().length() > 0) {
            return onActionRemoveComment(chat);
        }

        //cancel order
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        return getDefaultResponse(chat);
    }

    @Lookup
    public CommentMessageHandler getCommentMessageHandler() {
        return null;
    }

    private BotMessage onActionAttachComment(Chat chat) {
        //to comment message handler
        log.info("Redirecting user {} to CommentMessageHandler", chat.getUser());
        CommentMessageHandler commentMessageHandler = getCommentMessageHandler();
        commentMessageHandler.setCart(getCart(chat));
        chat.setMessageHandler(commentMessageHandler);
        return commentMessageHandler.getDefaultResponse(chat);
    }

    private BotMessage onActionRemoveComment(Chat chat) {
        //remove comment
        cartService.get((OffSiteUser) chat.getUser()).ifPresent(cart -> {
            cart.removeComment();
            cartService.save(cart);
        });
        log.info("User {} removed cart comment", chat.getUser());
        return getDefaultResponse(chat);
    }

    private BotMessage onActionCancel(Chat chat) {
        //delete cart
        cartService.delete((OffSiteUser) chat.getUser());

        //to home message handler
        log.info("Redirecting user {} to HomeMessageHandler", chat.getUser());
        chat.setMessageHandler(homeMessageHandler);
        return new BotMessage(MESSAGE_ON_CANCEL, homeMessageHandler.getKeyboard());
    }

    private Cart getCart(Chat chat) {
        OffSiteUser user = (OffSiteUser) chat.getUser();
        return cartService.get(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomer(user);
            return cartService.save(newCart);
        });
    }

    private String buildCartDescription(Cart cart) {
        if (cart.getComment().length() > 0) return cart.getComment();
        return "Корзина пуста";
    }

    private Keyboard buildKeyboard(Cart cart) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();
//        Set<Product> products = productService.getAll();
//
//        //products buttons
//        for (int i = 0; i < products.size() && i < 5; i++) {
//            keyboardBuilder.add(
//                    new KeyboardButton()
//                            .setAction(new KeyboardButtonAction()
//                                    .setLabel(products.get(i).getName())
//                                    .setType(KeyboardButtonActionType.TEXT)),
//                    0, i);
//        }
//
//        //todo add "more products" button
//
        //comment button
        if (cart.getComment().length() == 0) {
            keyboardBuilder.add(new Button(ACTION_ATTACH_COMMENT));
        } else {
            keyboardBuilder.add(new Button(ACTION_REMOVE_COMMENT));
        }
//
//        //payment button
//        if (!order.isEmpty()) {
//            keyboardBuilder.add(new KeyboardButton()
//                    .setAction(new KeyboardButtonAction()
//                            .setLabel(ACTION_TO_PAYMENT)
//                            .setType(KeyboardButtonActionType.TEXT))
//                    .setColor(KeyboardButtonColor.PRIMARY)
//            );
//        }
//
        //cancel button
        keyboardBuilder.add(new Button(ACTION_CANCEL, ButtonType.NEGATIVE));

        return keyboardBuilder.build();
    }
}
