package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.OrderedProduct;
import com.aiecel.gubernskyprintingdvor.model.Product;
import com.aiecel.gubernskyprintingdvor.service.OrderService;
import com.aiecel.gubernskyprintingdvor.service.ProductService;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class OrderVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE = "Хотите что-то отпечатать? Просто взять товару? Как вамъ угодно!";

    public static final String MESSAGE_QUANTITY = "Сколько хотите?";

    private final VkUserService vkUserService;
    private final ProductService productService;
    private final OrderService orderService;
    private final Order order;

    private OrderedProduct orderedProduct;

    @Autowired
    public OrderVkMessageHandler(VkUserService vkUserService, ProductService productService, OrderService orderService) {
        this.vkUserService = vkUserService;
        this.productService = productService;
        this.orderService = orderService;
        this.order = new Order();
    }

    public void setVkUserId(int vkId) {
        order.setCustomer(vkUserService.getUser(vkId));
    }

    @Override
    public Message onMessage(Message message, Chatter<Message> chatter) {
        List<Product> products = productService.getAll();

        if (orderedProduct != null) {
            try {
                orderedProduct.setQuantity(Integer.parseInt(message.getText()));
                order.addProduct(orderedProduct);
                orderedProduct.setOrder(order);
                orderedProduct = null;
            } catch (NumberFormatException e) {
                return constructVkMessage("Ещё раз сколько?");
            }
        } else {
            if (products.stream().anyMatch(product -> message.getText().equals(product.getName()))) {
                orderedProduct = new OrderedProduct();
                orderedProduct.setProduct(productService.getProduct(message.getText()).orElse(null));
                return constructVkMessage(MESSAGE_QUANTITY);
            }
        }
        if (order.getOrderedProducts().size() > 0) {
            if (message.getText().equals("Сделать заказ")) {
                order.setOrderDateTime(ZonedDateTime.now());
                orderService.save(order);
                chatter.setMessageHandler(message.getFromId(), getHomeVkMessageHandler());
                return constructVkMessage("Спасибо за заказ!");
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Вашъ заказ:\n");
            for (OrderedProduct product : order.getOrderedProducts()) {
                stringBuilder
                        .append(product.getProduct().getName())
                        .append(" - ")
                        .append(product.getQuantity())
                        .append("шт.\n");
            }
            stringBuilder.append("Что-то ещё?");

            return constructVkMessage(stringBuilder.toString(), mainKeyboard(productService.getAll(), true));
        }
        return constructVkMessage(DEFAULT_MESSAGE, mainKeyboard(productService.getAll(), false));
    }

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
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

        if (orderButton) {
            List<KeyboardButton> row2 = new ArrayList<>();
            row2.add(
                    new KeyboardButton().setAction(
                            new KeyboardButtonAction()
                                    .setLabel("Сделать заказ")
                                    .setType(KeyboardButtonActionType.TEXT)
                    ).setColor(KeyboardButtonColor.PRIMARY)
            );
            buttons.add(row2);
        }

        keyboard.setButtons(buttons);
        keyboard.setOneTime(true);
        return keyboard;
    }
}
