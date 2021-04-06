package com.aiecel.gubernskytypography.bot.vk.handler;

import com.aiecel.gubernskytypography.bot.Chatter;
import com.aiecel.gubernskytypography.bot.vk.keyboard.KeyboardBuilder;
import com.aiecel.gubernskytypography.model.OrderStatus;
import com.aiecel.gubernskytypography.service.OrderService;
import com.aiecel.gubernskytypography.service.PricingService;
import com.aiecel.gubernskytypography.service.UserService;
import com.vk.api.sdk.objects.messages.*;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
@Scope("prototype")
@Setter
public class PaymentVkMessageHandler extends OrderDependedVkMessageHandler {
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

    public PaymentVkMessageHandler(UserService userService, OrderService orderService, PricingService pricingService) {
        this.userService = userService;
        this.orderService = orderService;
        this.pricingService = pricingService;
    }

    @Override
    public Message getDefaultMessage() {
        BigDecimal debt = userService.getDebt(getOrder().getCustomer().getId());
        String message = MESSAGE_TITLE + "\n\n";

        message += String.format(MESSAGE_TOTAL, pricingService.calculatePrice(getOrder()));

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
            if (userService.getDebt(getOrder().getCustomer().getId()).compareTo(new BigDecimal(30)) < 0) {
                getOrder().setOrderDateTime(ZonedDateTime.now());
                getOrder().setPrice(pricingService.calculatePrice(getOrder()));
                getOrder().setPaid(false);
                getOrder().setStatus(OrderStatus.PENDING);
                orderService.save(getOrder());

                HomeVkMessageHandler homeVkMessageHandler = messageHandlerFactory().get(HomeVkMessageHandler.class);
                chatter.setMessageHandler(message.getFromId(), homeVkMessageHandler);
                return constructVkMessage(MESSAGE_ON_ORDER, homeVkMessageHandler.keyboard());
            }
        }

        //back to order
        if (message.getText().equalsIgnoreCase(ACTION_BACK_TO_ORDER)) {
            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
        }

        //cancel order
        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
            HomeVkMessageHandler homeVkMessageHandler = messageHandlerFactory().get(HomeVkMessageHandler.class);
            chatter.setMessageHandler(message.getFromId(), homeVkMessageHandler);
            return constructVkMessage(MESSAGE_ON_CANCEL, homeVkMessageHandler.keyboard());
        }

        return getDefaultMessage();
    }

    public static Keyboard keyboard(boolean debtButton) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        keyboardBuilder.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel("vk pay button")
                        .setType(KeyboardButtonActionType.TEXT))
                .setColor(KeyboardButtonColor.PRIMARY)
        );

        if (debtButton) {
            keyboardBuilder.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction()
                            .setLabel(ACTION_DEBT)
                            .setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.PRIMARY)
            );
        }

        keyboardBuilder
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_BACK_TO_ORDER)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.DEFAULT))
                .add(new KeyboardButton()
                        .setAction(new KeyboardButtonAction()
                                .setLabel(ACTION_CANCEL)
                                .setType(KeyboardButtonActionType.TEXT))
                        .setColor(KeyboardButtonColor.NEGATIVE));

        return keyboardBuilder.build();
    }
}
