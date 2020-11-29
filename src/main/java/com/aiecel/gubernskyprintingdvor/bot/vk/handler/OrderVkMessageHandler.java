package com.aiecel.gubernskyprintingdvor.bot.vk.handler;

import com.aiecel.gubernskyprintingdvor.bot.Chatter;
import com.aiecel.gubernskyprintingdvor.bot.vk.keyboard.KeyboardBuilder;
import com.aiecel.gubernskyprintingdvor.exception.FileDownloadException;
import com.aiecel.gubernskyprintingdvor.exception.DocumentBuildException;
import com.aiecel.gubernskyprintingdvor.exception.ExtensionNotSupportedException;
import com.aiecel.gubernskyprintingdvor.model.*;
import com.aiecel.gubernskyprintingdvor.service.DocumentService;
import com.aiecel.gubernskyprintingdvor.service.PricingService;
import com.aiecel.gubernskyprintingdvor.service.ProductService;
import com.aiecel.gubernskyprintingdvor.service.VkUserService;
import com.vk.api.sdk.objects.docs.Doc;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class OrderVkMessageHandler extends VkMessageHandler {
    public static final String DEFAULT_MESSAGE = "\uD83D\uDCF0 Хотите что-то отпечатать? Просто взять товару? Как вамъ угодно!";

    public static final String MESSAGE_ORDER = "\uD83D\uDED2 Ваша корзина:\n";
    public static final String MESSAGE_ORDERED_DOCUMENT = "• Документъ \"%s\" - %d шт.";
    public static final String MESSAGE_ORDERED_PRODUCT = "• %s - %d шт.";
    public static final String MESSAGE_TOTAL = "\uD83D\uDCB0 ИТОГО: %s руб.";
    public static final String MESSAGE_COMMENT = "✏ Комментарий к заказу: \"%s\"";
    public static final String MESSAGE_ON_CANCEL = "\uD83E\uDD25 Эх, ну ладно. Ежели что-то хотите сделать - только напишите!";
    public static final String MESSAGE_WHAT_ELSE = "Что-то ещё хотите? Не стесняйтесь, берите!";
    public static final String MESSAGE_SINGLE_DOCUMENT = "Пожалуйста, прикрепити эти документы раздельно, чтобы мы могли спросити количество копий для каждаго";
    public static final String MESSAGE_DOCUMENT_BUILD_ERROR = "Даже и не знаемъ, что сказати...\n" +
            "Произошла беда при обработке вашего дакумента. Обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_DOWNLOAD_ERROR =
            "Кажется, произошелъ обрыв телеграфнаго провода, ибо намъ не удаётся получить ваш дакумент.\n" +
                    "Попробуйте позже, или, если проблема повторится, обратитесь к управленцамъ двора для решения проблемы";

    public static final String MESSAGE_DOCUMENT_NOT_SUPPORTED = "Мы видимъ документъ, да не узнаем почерк... \nТакой формат мы пока не воспринимаемъ";

    public static final String ACTION_TO_PAYMENT = "\uD83D\uDCB4 Оплатить!";
    public static final String ACTION_COMMENT = "✏ Прикрепить комментарий";
    public static final String ACTION_CANCEL = "\uD83D\uDEAB Отменить заказъ";

    private final VkUserService vkUserService;
    private final DocumentService documentService;
    private final ProductService productService;
    private final PricingService pricingService;
    private Order order;

    public OrderVkMessageHandler(VkUserService vkUserService,
                                 DocumentService documentService,
                                 ProductService productService,
                                 PricingService pricingService) {
        this.vkUserService = vkUserService;
        this.documentService = documentService;
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
            for (OrderedDocument document : order.getOrderedDocuments()) {
                stringBuilder
                        .append(String.format(MESSAGE_ORDERED_DOCUMENT, document.getDocument().getTitle(), document.getQuantity()))
                        .append("\n");
            }
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
                PaymentVkMessageHandler paymentVkMessageHandler = messageHandlerFactory().get(PaymentVkMessageHandler.class);
                paymentVkMessageHandler.setOrder(order);
                return proceedToNewMessageHandler(message.getFromId(), paymentVkMessageHandler, chatter);
            }
        }

        //add documents
        if (message.getAttachments().size() > 0) {
            List<MessageAttachment> documentAttachments = message.getAttachments().stream()
                    .filter(attachment -> attachment.getType() == MessageAttachmentType.DOC)
                    .collect(Collectors.toList());

            if (documentAttachments.size() > 1) {
                return constructVkMessage(MESSAGE_SINGLE_DOCUMENT, mainKeyboard(productService.getAll(), !order.isEmpty()));
            }

            for (MessageAttachment documentAttachment : documentAttachments) {
                Doc doc = documentAttachment.getDoc();
                try {
                    Document document = documentService.constructDocument(doc.getTitle(), doc.getExt(), doc.getUrl());
                    document.setOwner(order.getCustomer());
                    OrderDocumentVkMessageHandler orderDocumentVkMessageHandler = messageHandlerFactory().get(OrderDocumentVkMessageHandler.class);
                    orderDocumentVkMessageHandler.setOrder(order);
                    orderDocumentVkMessageHandler.setDocument(document);
                    return proceedToNewMessageHandler(message.getFromId(), orderDocumentVkMessageHandler, chatter);
                } catch (DocumentBuildException e) {
                    return constructVkMessage(MESSAGE_DOCUMENT_BUILD_ERROR, mainKeyboard(productService.getAll(), !order.isEmpty()));
                } catch (FileDownloadException e) {
                    return constructVkMessage(MESSAGE_DOCUMENT_DOWNLOAD_ERROR, mainKeyboard(productService.getAll(), !order.isEmpty()));
                } catch (ExtensionNotSupportedException e) {
                    return constructVkMessage(MESSAGE_DOCUMENT_NOT_SUPPORTED, mainKeyboard(productService.getAll(), !order.isEmpty()));
                }
            }
        }

        //add product
        if (productService.getAll().stream().anyMatch(product -> message.getText().equals(product.getName()))) {
            OrderProductVkMessageHandler orderProductVkMessageHandler = messageHandlerFactory().get(OrderProductVkMessageHandler.class);
            orderProductVkMessageHandler.setOrder(order);
            orderProductVkMessageHandler.setProduct(productService.getProduct(message.getText()).orElseThrow(
                    () -> new RuntimeException("СМЕРТ"))
            );
            return proceedToNewMessageHandler(message.getFromId(), orderProductVkMessageHandler, chatter);
        }

        //comment order
        if (message.getText().equalsIgnoreCase(ACTION_COMMENT)) {
            CommentOrderVkMessageHandler commentOrderVkMessageHandler = messageHandlerFactory().get(CommentOrderVkMessageHandler.class);
            commentOrderVkMessageHandler.setOrder(order);
            return proceedToNewMessageHandler(message.getFromId(), commentOrderVkMessageHandler, chatter);
        }

        //cancel order
        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
            HomeVkMessageHandler homeVkMessageHandler = messageHandlerFactory().get(HomeVkMessageHandler.class);
            chatter.setMessageHandler(message.getFromId(), homeVkMessageHandler);
            return constructVkMessage(MESSAGE_ON_CANCEL, homeVkMessageHandler.keyboard());
        }

        return getDefaultMessage();
    }

    public static Keyboard mainKeyboard(List<Product> products, boolean paymentButton) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        //products buttons
        for (int i = 0; i < products.size() && i < 5; i++) {
            keyboardBuilder.add(
                    new KeyboardButton()
                            .setAction(new KeyboardButtonAction()
                                    .setLabel(products.get(i).getName())
                                    .setType(KeyboardButtonActionType.TEXT)),
                    0, i);
        }

        //todo add "more products" button

        //comment button
        keyboardBuilder.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(ACTION_COMMENT)
                        .setType(KeyboardButtonActionType.TEXT))
                .setColor(KeyboardButtonColor.DEFAULT)
        );

        //payment button
        if (paymentButton) {
            keyboardBuilder.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction()
                            .setLabel(ACTION_TO_PAYMENT)
                            .setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.PRIMARY)
            );
        }

        //cancel button
        keyboardBuilder.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(ACTION_CANCEL)
                        .setType(KeyboardButtonActionType.TEXT))
                .setColor(KeyboardButtonColor.NEGATIVE)
        );

        return keyboardBuilder.build();
    }
}
