package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderStatus;
import com.aiecel.gubernskyprintingdvor.service.OrderService;
import com.aiecel.gubernskyprintingdvor.service.PricingService;
import com.aiecel.gubernskyprintingdvor.service.UserService;
import com.vk.api.sdk.objects.messages.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Setter
public class PaymentVkMessageHandler extends VkMessageHandler {
    public static final String MESSAGE_TITLE =
            "\uD83D\uDC46\uD83C\uDFFB О плате глаголятъ либо добро, либо никак.\n" +
            "Мы не стыдимся и говорим так, как есть";

    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 Итого к оплате: %s руб.";
    public static final String MESSAGE_NO_DEBT =
            "\uD83D\uDE0E У васъ нет долгов! \n" +
            "С радованием предоставимъ вам бумагу в долг!";

    public static final String MESSAGE_SMALL_DEBT =
            "\uD83D\uDE11 Вашъ долг составляетъ %s руб. \n" +
                    "Мы считаемъ, что это небольшая сумма и позволяем вамъ оплатить этотъ заказ в долг!";

    public static final String MESSAGE_BIG_DEBT =
            "\uD83D\uDE28 Вашъ долг составляетъ аш %s руб. \n" +
                    "\"Как не вертись, с должником расплатись!\" - велит русская поговорка. Оплатить этот заказъ вы можете только через VK Pay";

    public static final String MESSAGE_ON_ORDER = "\uD83D\uDE0F Вот так то лучше! Заказ отправлен, ежели ещё остались какие-то дела - милости просим!";
    public static final String MESSAGE_ON_CANCEL = "\uD83E\uDD25 Эх, ну ладно. Ежели что-то хотите сделать - только напишите!";

    public static final String ACTION_DEBT = "✍\uD83C\uDFFB Запишите в долгъ";
    public static final String ACTION_BACK_TO_ORDER = "\uD83D\uDE44 Хочу кое-что ещё!";
    public static final String ACTION_CANCEL = "\uD83D\uDEAB Я передумал заказывать";

    private final UserService userService;
    private final OrderService orderService;
    private final PricingService pricingService;

    private Order order;

    public PaymentVkMessageHandler(UserService userService, OrderService orderService, PricingService pricingService) {
        this.userService = userService;
        this.orderService = orderService;
        this.pricingService = pricingService;
    }

    @Override
    public Message getDefaultMessage() {
        BigDecimal debt = userService.getDebt(order.getCustomer().getId());
        String message = MESSAGE_TITLE + "\n\n";

        message += String.format(MESSAGE_TOTAL, pricingService.calculatePrice(order));

        message += "\n\n";

        if (debt.compareTo(new BigDecimal(30)) >= 0) {
            message += String.format(MESSAGE_BIG_DEBT, debt);
        } else if (debt.compareTo(BigDecimal.ZERO) > 0) {
            message += String.format(MESSAGE_SMALL_DEBT, debt);
        } else {
            message += MESSAGE_NO_DEBT;
        }

        return constructVkMessage(message, keyboard(debt.compareTo(new BigDecimal(30)) < 0));
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        //vk pay
        if (message.getText().equalsIgnoreCase("vk pay button")) {
            return getDefaultMessage();
        }

        //debt
        if (message.getText().equalsIgnoreCase(ACTION_DEBT)) {
            if (userService.getDebt(order.getCustomer().getId()).compareTo(new BigDecimal(30)) < 0) {
                order.setOrderDateTime(ZonedDateTime.now());
                order.setPrice(pricingService.calculatePrice(order));
                order.setPaid(false);
                order.setStatus(OrderStatus.PENDING);
                orderService.save(order);
                chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
                return constructVkMessage(MESSAGE_ON_ORDER, HomeVkMessageHandler.keyboard());
            }
        }

        //back to order
        if (message.getText().equalsIgnoreCase(ACTION_BACK_TO_ORDER)) {
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        }

        //cancel order
        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
            chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
            return constructVkMessage(MESSAGE_ON_CANCEL, HomeVkMessageHandler.keyboard());
        }

        return getDefaultMessage();
    }

    public static Keyboard keyboard(boolean debtButton) {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> buttons = new ArrayList<>();

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel("vk pay button")
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.PRIMARY)
        );
        buttons.add(row1);

        if (debtButton) {
            List<KeyboardButton> row2 = new ArrayList<>();
            row2.add(
                    new KeyboardButton().setAction(
                            new KeyboardButtonAction()
                                    .setLabel(ACTION_DEBT)
                                    .setType(KeyboardButtonActionType.TEXT)
                    ).setColor(KeyboardButtonColor.PRIMARY)
            );
            buttons.add(row2);
        }

        List<KeyboardButton> row3 = new ArrayList<>();
        row3.add(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction()
                                .setLabel(ACTION_BACK_TO_ORDER)
                                .setType(KeyboardButtonActionType.TEXT)
                ).setColor(KeyboardButtonColor.DEFAULT)
        );
        buttons.add(row3);

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

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
    }
}
