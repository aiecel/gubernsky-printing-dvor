package com.aiecel.gubernskytypography.bot.vk.handler;

import com.aiecel.gubernskytypography.bot.Chatter;
import com.aiecel.gubernskytypography.bot.vk.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.OrderedDocument;
import com.aiecel.gubernskytypography.model.OrderedProduct;
import com.aiecel.gubernskytypography.model.VkUser;
import com.aiecel.gubernskytypography.service.OrderService;
import com.aiecel.gubernskytypography.service.VkUserService;
import com.vk.api.sdk.objects.messages.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Scope("prototype")
@AllArgsConstructor
public class HomeVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE =
            "Губернский Печатный Дворъ привѣтствуетъ тебя, засельщина земли Губернской! \n" +
                    "Возжелаешь ли отпечатать бумаги свои? Нашъ станъ готовъ неустанно трудиться денно и нощно";

    public static final String MESSAGE_GREETINGS = "\uD83C\uDF1A На добр приветъ добр и ответъ!";
    public static final String MESSAGE_ORDERS_LIST_TITLE = "\uD83D\uDCBC Вотъ все ваши заказы:";
    public static final String MESSAGE_ORDER_TITLE = "Заказ %s: %s, %s";
    public static final String MESSAGE_ORDER_PAID = "оплаченъ ✅";
    public static final String MESSAGE_ORDER_NOT_PAID = "НЕ ОПЛАЧЕНЪ ⚠";
    public static final String MESSAGE_ORDERED_DOCUMENT = "• Документъ \"%s\" - %d шт.";
    public static final String MESSAGE_ORDERED_PRODUCT = "• %s - %d шт.";
    public static final String MESSAGE_COMMENT = "✏ Комментарий к заказу: \"%s\"";
    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 Стоимость: %s руб.";

    public static final String ACTION_ORDER = "\uD83D\uDCE6 Сделать заказъ";
    public static final String ACTION_ORDERS_LIST = "\uD83D\uDCBC Моя корреспонденция";
    public static final String ACTION_FEEDBACK = "\uD83D\uDCD6 Книга жалобъ";

    public final VkUserService vkUserService;
    public final OrderService orderService;

    @Override
    public Message getDefaultMessage() {
        return constructVkMessage(DEFAULT_MESSAGE, keyboard());
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //proceed to order handler
        if (message.getText().equals(ACTION_ORDER)) {
            OrderVkMessageHandler messageHandler = messageHandlerFactory().get(OrderVkMessageHandler.class);
            messageHandler.setVkUserId(message.getFromId());
            return proceedToNewMessageHandler(message.getFromId(), messageHandler, chatter);
        }

        //see orders
        if (message.getText().equals(ACTION_ORDERS_LIST)) {
            VkUser vkUser = vkUserService.getUser(message.getFromId());
            List<Order> orders = orderService.getAllByCustomerId(vkUser.getId());

            if (orders.size() > 0) {
                StringBuilder orderStringBuilder = new StringBuilder();
                orderStringBuilder.append(MESSAGE_ORDERS_LIST_TITLE).append("\n");
                Order order;
                for (int i = 0; i < orders.size(); i++) {
                    order = orders.get(i);
                    orderStringBuilder.append(String.format(MESSAGE_ORDER_TITLE, i + 1, order.getStatus().getDescription(), order.isPaid() ? MESSAGE_ORDER_PAID : MESSAGE_ORDER_NOT_PAID)).append("\n");
                    for (OrderedDocument document : order.getOrderedDocuments()) {
                        orderStringBuilder
                                .append(String.format(MESSAGE_ORDERED_DOCUMENT, document.getDocument().getTitle(), document.getQuantity()))
                                .append("\n");
                    }
                    for (OrderedProduct product : order.getOrderedProducts()) {
                        orderStringBuilder
                                .append(String.format(MESSAGE_ORDERED_PRODUCT, product.getProduct().getName(), product.getQuantity()))
                                .append("\n");
                    }

                    if (order.getComment() != null && order.getComment().length() > 0) {
                        orderStringBuilder.append(String.format(MESSAGE_COMMENT, order.getComment())).append("\n");
                    }

                    orderStringBuilder.append(String.format(MESSAGE_TOTAL, order.getPrice())).append("\n\n");
                }
                return constructVkMessage(orderStringBuilder.toString(), keyboard());
            }
        }

        //proceed to feedback handler
        if (message.getText().equals(ACTION_FEEDBACK)) {
            return proceedToNewMessageHandler(
                    message.getFromId(),
                    messageHandlerFactory().get(FeedbackVkMessageHandler.class),
                    chatter
            );
        }

        //greetings
        if (isMessageContainsGreetings(message.getText())) {
            return constructVkMessage(MESSAGE_GREETINGS, keyboard());
        }

        return constructVkMessage(DEFAULT_MESSAGE, keyboard());
    }

    public Keyboard keyboard() {
        return new KeyboardBuilder()
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_ORDER)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.PRIMARY))
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_ORDERS_LIST)
                                .setType(KeyboardButtonActionType.TEXT)))
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_FEEDBACK)
                                .setType(KeyboardButtonActionType.TEXT)))
                .build();
    }

    private boolean isMessageContainsGreetings(String message) {
        String messageLowerCase = message.toLowerCase();

        String[] greetingsToContainsCheck = new String[]{
                "прив",
                "добрый ден",
                "добрый вечер",
                "салам",
                "даров",
                "доров",
                "здрав",
                "хай",
                "hello",
                "иншалла",
                "дратути",
                "вечер в хату",
                "привѣт",
                "хелло",
        };

        String[] greetingsToEqualsCheck = new String[]{
                "ку", "hi"
        };

        return Arrays.stream(greetingsToContainsCheck).anyMatch(messageLowerCase::contains) || Arrays.stream(greetingsToEqualsCheck).allMatch(messageLowerCase::equals);
    }
}
