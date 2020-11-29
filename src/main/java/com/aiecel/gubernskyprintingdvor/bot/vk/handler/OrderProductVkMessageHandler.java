package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderedProduct;
import com.aiecel.gubernskyprintingdvor.model.Product;
import com.vk.api.sdk.objects.messages.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Setter
public class OrderProductVkMessageHandler extends VkMessageHandler {
    public static final String MESSAGE_ASK_QUANTITY = "Сколько пожелаете?";
    public static final String MESSAGE_ASK_QUANTITY_AGAIN = "Сколько ещё разъ?";
    public static final String MESSAGE_ZERO_QUANTITY = "Ну, ноль так ноль. Что-то ещё?"; //not used for now
    public static final String MESSAGE_TOO_MUCH_QUANTITY = "\uD83D\uDE33 Ух! Столь много не можемъ вамъ выдать! Давайте поменьше";
    public static final String MESSAGE_PRODUCT_PRICE = "\uD83D\uDE0F Сегодня у насъ %s стоитъ %s рублей!";

    public static final String ACTION_CANCEL = "\uD83D\uDEAB Ладно, не буду";
    public static final String ACTION_CHECK_PRICE = "\uD83D\uDC40 А по чёмъ штука?";

    private Order order;
    private Product product;

    @Override
    public Message getDefaultMessage() {
        return constructVkMessage(MESSAGE_ASK_QUANTITY, keyboard());
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //cancel ordering the product
        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        }

        //check the price of the product
        if (message.getText().equalsIgnoreCase(ACTION_CHECK_PRICE)) {
            return constructVkMessage(
                    String.format(MESSAGE_PRODUCT_PRICE, product.getName().toLowerCase(), product.getPrice()) +
                            "\n" + MESSAGE_ASK_QUANTITY,
                    keyboard()
            );
        }

        //read the quantity
        try {
            //parse number
            int quantity = Integer.parseInt(message.getText());

            if (quantity < 0) {
                //negative numbers are not allowed here
                throw new NumberFormatException();
            } else if (quantity > 1000) {
                return constructVkMessage(MESSAGE_TOO_MUCH_QUANTITY, keyboard());
            } else if (quantity == 0) {
                return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
            }

            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(product);
            orderedProduct.setQuantity(quantity);
            orderedProduct.setOrder(order);
            order.addOrderedProduct(orderedProduct);

            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        } catch (NumberFormatException e) {
            return constructVkMessage(MESSAGE_ASK_QUANTITY_AGAIN, keyboard());
        }
    }

    public static Keyboard keyboard() {

        Keyboard keyboard = new Keyboard();

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_CHECK_PRICE)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.DEFAULT)
        );

        List<KeyboardButton> row2 = new ArrayList<>();
        row2.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_CANCEL)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.NEGATIVE)
        );

        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row1);
        buttons.add(row2);

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
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
