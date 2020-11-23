package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderedProduct;
import com.aiecel.gubernskyprintingdvor.model.Product;
import com.aiecel.gubernskyprintingdvor.service.PricingService;
import com.aiecel.gubernskyprintingdvor.service.ProductService;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class OrderVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE = "\uD83D\uDCF0 Хотите что-то отпечатать? Просто взять товару? Как вамъ угодно!";

    public static final String MESSAGE_ORDER = "\uD83D\uDED2 Ваша корзина:\n";
    public static final String MESSAGE_ORDERED_PRODUCT = "• %s - %d шт.";
    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 ИТОГО: %s руб.";
    public static final String MESSAGE_COMMENT = "✏ Комментарий к заказу: %s";
    public static final String MESSAGE_ON_CANCEL = "\uD83E\uDD25 Эх, ну ладно. Ежели что-то хотите сделать - только напишите!";
    public static final String MESSAGE_WHAT_ELSE = "Что-то ещё хотите? Не стесняйтесь, берите!";

    public static final String ACTION_TO_PAYMENT = "\uD83D\uDCB4 Оплатить!";
    public static final String ACTION_COMMENT = "✏ Прикрепить комментарий";
    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить заказъ";

    private final VkUserService vkUserService;
    private final ProductService productService;
    private final PricingService pricingService;
    private Order order;

    public OrderVkMessageHandler(VkUserService vkUserService,
                                 ProductService productService,
                                 PricingService pricingService) {
        this.vkUserService = vkUserService;
        this.productService = productService;
        this.pricingService = pricingService;
        this.order = new Order();
    }

    public void setVkUserId(int vkId) {
        order.setCustomer(vkUserService.getUser(vkId));
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Message getDefaultMessage() {
        if (order.isEmpty()) {
            return constructVkMessage(DEFAULT_MESSAGE, mainKeyboard(productService.getAll(), false));
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(MESSAGE_ORDER).append("\n");
            for (OrderedProduct product : order.getOrderedProducts()) {
                stringBuilder
                        .append(String.format(MESSAGE_ORDERED_PRODUCT, product.getProduct().getName(), product.getQuantity()))
                        .append("\n");
            }
            stringBuilder.append(String.format(MESSAGE_TOTAL, pricingService.calculatePrice(order))).append("\n\n");

            if (order.getComment() != null && order.getComment().length() > 0) {
                stringBuilder.append(String.format(MESSAGE_COMMENT, order.getComment())).append("\n\n");
            }

            stringBuilder.append(MESSAGE_WHAT_ELSE);

            return constructVkMessage(stringBuilder.toString(), mainKeyboard(productService.getAll(), true));
        }
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        if (!order.isEmpty()) {
            //continue to payment
            if (message.getText().equals(ACTION_TO_PAYMENT)) {
                PaymentVkMessageHandler paymentVkMessageHandler = getPaymentVkMessageHandler();
                paymentVkMessageHandler.setOrder(order);
                return proceedToNewMessageHandler(message.getFromId(), paymentVkMessageHandler, chatter);
            }
        }

        //add product
        if (productService.getAll().stream().anyMatch(product -> message.getText().equals(product.getName()))) {
            OrderProductVkMessageHandler orderProductVkMessageHandler = getOrderProductVkMessageHandler();
            orderProductVkMessageHandler.setOrder(order);
            orderProductVkMessageHandler.setProduct(productService.getProduct(message.getText()).orElseThrow(
                    () -> new RuntimeException("СМЕРТ"))
            );
            return proceedToNewMessageHandler(message.getFromId(), orderProductVkMessageHandler, chatter);
        }

        //comment order
        if (message.getText().equalsIgnoreCase(ACTION_COMMENT)) {
            CommentOrderVkMessageHandler commentOrderVkMessageHandler = getCommentOrderVkMessageHandler();
            commentOrderVkMessageHandler.setOrder(order);
            return proceedToNewMessageHandler(message.getFromId(), commentOrderVkMessageHandler, chatter);
        }

        //cancel order
        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
            chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
            return constructVkMessage(MESSAGE_ON_CANCEL, HomeVkMessageHandler.keyboard());
        }

        return getDefaultMessage();
    }

    public static Keyboard mainKeyboard(List<Product> products, boolean orderButton) {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> buttons = new ArrayList<>();

        if (products.size() > 0) {
            List<KeyboardButton> row1 = new ArrayList<>();
            products.forEach(product ->
                    row1.add(
                            new KeyboardButton().setAction(
                                    new KeyboardButtonAction()
                                            .setLabel(product.getName())
                                            .setType(KeyboardButtonActionType.TEXT)
                            )
                    )
            );
            buttons.add(row1);
        }

        List<KeyboardButton> row2 = new ArrayList<>();
        row2.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_COMMENT)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.DEFAULT)
        );
        buttons.add(row2);

        if (orderButton) {
            List<KeyboardButton> row3 = new ArrayList<>();
            row3.add(
                    new KeyboardButton().setAction(
                            new KeyboardButtonAction()
                                    .setLabel(ACTION_TO_PAYMENT)
                                    .setType(KeyboardButtonActionType.TEXT)
                    ).setColor(KeyboardButtonColor.PRIMARY)
            );
            buttons.add(row3);
        }

        List<KeyboardButton> row4 = new ArrayList<>();
        row4.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_CANCEL)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.NEGATIVE)
        );
        buttons.add(row4);

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
    }

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
    }

    @Lookup
    public CommentOrderVkMessageHandler getCommentOrderVkMessageHandler() {
        return null;
    }

    @Lookup
    public OrderProductVkMessageHandler getOrderProductVkMessageHandler() {
        return null;
    }

    @Lookup
    public PaymentVkMessageHandler getPaymentVkMessageHandler() {
        return null;
    }
}
