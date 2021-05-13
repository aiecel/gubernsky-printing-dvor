package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.aiecel.gubernskytypography.bot.api.keyboard.ButtonType;
import com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard;
import com.aiecel.gubernskytypography.bot.api.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.bot.model.Cart;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.model.Product;
import com.aiecel.gubernskytypography.bot.service.CartService;
import com.aiecel.gubernskytypography.bot.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CartMessageHandler extends AbstractMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "\uD83D\uDCF0 Хотите что-то отпечатать? Просто взять товару? Как вамъ угодно!";

    public static final String MESSAGE_CART = "\uD83D\uDED2 Ваша корзина:";
    public static final String MESSAGE_WHAT_ELSE = "Что-то ещё хотите? Не стесняйтесь, берите!";

    public static final String MESSAGE_ON_CANCEL =
            "\uD83E\uDD25 Эх, ну ладно. Ежели что-то хотите сделать - только напишите!";

    public static final String MESSAGE_SINGLE_DOCUMENT =
            "Пожалуйста, прикрепити эти документы раздельно, чтобы мы могли спросити количество копий для каждого";

    public static final String MESSAGE_DOCUMENT_BUILD_ERROR = "Даже и не знаемъ, что сказати...\n" +
            "Произошла беда при обработке вашего документа. Обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_DOWNLOAD_ERROR =
            "Кажется, произошелъ обрыв телеграфного провода, ибо намъ не удаётся получить ваш документъ.\n" +
                    "Попробуйте позже, или, если проблема повторится, " +
                    "обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_NOT_SUPPORTED =
            "Мы видимъ документъ, да не узнаем почерк... \nТакой формат мы пока не воспринимаемъ";

    public static final String ACTION_TO_PAYMENT = "\uD83D\uDCB4 Оплатить!";
    public static final String ACTION_ATTACH_COMMENT = "✏ Прикрепить комментарий";
    public static final String ACTION_REMOVE_COMMENT = "✏ Убрать комментарий";
    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить заказъ";

    @Setter(onMethod_ = @Autowired) //to avoid circular dependency
    private HomeMessageHandler homeMessageHandler;

    private final CartService cartService;
    private final ProductService productService;

    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        Optional<Cart> cartOptional = cartService.find((OffSiteUser) chat.getUser());
        return cartOptional.map(
                cart -> new BotMessage(
                        MESSAGE_CART + "\n" + cart.toPrettyString() + "\n\n" + MESSAGE_WHAT_ELSE, buildKeyboard(chat.getUser())
                )
        ).orElseGet(() -> new BotMessage(DEFAULT_MESSAGE, buildKeyboard(chat.getUser())));
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        //add product
        List<Product> products = productService.getAll();
        for (Product product : products) {
            if (product.getName().equals(message.getText())) {
                return onActionAddProduct(product, chat);
            }
        }

        Optional<Cart> cartOptional = cartService.find((OffSiteUser) chat.getUser());

        //attach comment
        if (message.getText().equals(ACTION_ATTACH_COMMENT)
                && (!cartOptional.isPresent() || cartOptional.get().getComment().length() == 0)) {
            return onActionAttachComment(chat);
        }

        //remove comment
        if (message.getText().equals(ACTION_REMOVE_COMMENT)
                && cartOptional.isPresent() && cartOptional.get().getComment().length() > 0) {
            return onActionRemoveComment(chat);
        }

        //cancel order
        if (message.getText().equals(ACTION_CANCEL)) {
            return onActionCancel(chat);
        }

        return getDefaultResponse(chat);
    }

    @Lookup
    public AddProductMessageHandler getAddProductMessageHandler() {
        return null;
    }

    @Lookup
    public CommentMessageHandler getCommentMessageHandler() {
        return null;
    }

    private BotMessage onActionAddProduct(Product product, Chat chat) {
        //to add product message handler
        log.info("Redirecting user {} to AddProductMessageHandler", chat.getUser());
        AddProductMessageHandler addProductMessageHandler = getAddProductMessageHandler();
        addProductMessageHandler.setProduct(product);
        chat.setMessageHandler(addProductMessageHandler);
        return addProductMessageHandler.getDefaultResponse(chat);
    }

    private BotMessage onActionAttachComment(Chat chat) {
        //to comment message handler
        log.info("Redirecting user {} to CommentMessageHandler", chat.getUser());
        CommentMessageHandler commentMessageHandler = getCommentMessageHandler();
        chat.setMessageHandler(commentMessageHandler);
        return commentMessageHandler.getDefaultResponse(chat);
    }

    private BotMessage onActionRemoveComment(Chat chat) {
        //remove comment
        cartService.removeCommentFromCart((OffSiteUser) chat.getUser());
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

    private Keyboard buildKeyboard(User user) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        //products buttons
        List<Product> products = productService.getAll();
        for (int i = 0; i < products.size() && i < 5; i++) {
            keyboardBuilder.add(new Button(products.get(i).getName()), 0, i);
        }
        //todo add "more products" button

        //comment button
        Optional<Cart> cartOptional = cartService.find((OffSiteUser) user);

        if (!cartOptional.isPresent() || cartOptional.get().getComment().length() == 0) {
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
