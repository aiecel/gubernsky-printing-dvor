package com.aiecel.gubernskytypography.bot.handler.old;

//import com.aiecel.gubernskytypography.bot.Chatter;
//import com.aiecel.gubernskytypography.bot.vk.keyboard.KeyboardBuilder;
//import com.aiecel.gubernskytypography.model.Document;
//import com.aiecel.gubernskytypography.model.OrderedDocument;
//import com.aiecel.gubernskytypography.service.ProductService;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Setter
public class OrderDocumentVkMessageHandler extends OrderDependedVkMessageHandler {
//    public static final String MESSAGE_DOCUMENT = "Документъ \"%s\", листовъ: %s";
//    public static final String MESSAGE_ASK_QUANTITY = "Сколько копий пожелаете?";
//    public static final String MESSAGE_ASK_QUANTITY_AGAIN = "Сколько ещё разъ?";
//    public static final String MESSAGE_ZERO_QUANTITY = "Ну, ноль так ноль. Что-то ещё?"; //not used for now
//    public static final String MESSAGE_TOO_MUCH_QUANTITY = "\uD83D\uDE33 Ух! Столь много не можемъ вамъ выдать! Давайте поменьше";
//    public static final String MESSAGE_PAGE_PRICE = "\uD83D\uDE0F Сегодня мы за листъ берёмъ %s руб.!";
//
//    public static final String ACTION_CANCEL = "\uD83D\uDEAB Ладно, не буду";
//    public static final String ACTION_CHECK_PRICE = "\uD83D\uDC40 А по чёмъ листъ?";
//
//    private final ProductService productService;
//
//    private Document document;
//
//    public OrderDocumentVkMessageHandler(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @Override
//    public Message getDefaultMessage() {
//        return constructVkMessage(String.format(MESSAGE_DOCUMENT, document.getTitle(), document.getPages()) + "\n" + MESSAGE_ASK_QUANTITY, keyboard());
//    }
//
//    @Override
//    public Message onMessage(Message message, Chatter<Message> chatter) {
//        //cancel ordering the product
//        if (message.getText().equalsIgnoreCase(ACTION_CANCEL)) {
//            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
//        }
//
//        //check the price of the product
//        if (message.getText().equalsIgnoreCase(ACTION_CHECK_PRICE)) {
//            return constructVkMessage(
//                    String.format(MESSAGE_PAGE_PRICE, productService.getPageProduct().getPrice()) +
//                            "\n" + MESSAGE_ASK_QUANTITY,
//                    keyboard()
//            );
//        }
//
//        //read the quantity
//        try {
//            //parse number
//            int quantity = Integer.parseInt(message.getText());
//
//            if (quantity < 0) {
//                //negative numbers are not allowed here
//                throw new NumberFormatException();
//            } else if (quantity > 50) {
//                return constructVkMessage(MESSAGE_TOO_MUCH_QUANTITY, keyboard());
//            } else if (quantity == 0) {
//                return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
//            }
//
//            OrderedDocument orderedDocument = new OrderedDocument();
//            orderedDocument.setDocument(document);
//            orderedDocument.setQuantity(quantity);
//            orderedDocument.setOrder(getOrder());
//            getOrder().getOrderedDocuments().add(orderedDocument);
//
//            return proceedToOrderVkMessageHandler(message.getFromId(), chatter);
//        } catch (NumberFormatException e) {
//            return constructVkMessage(MESSAGE_ASK_QUANTITY_AGAIN, keyboard());
//        }
//    }
//
//    public static Keyboard keyboard() {
//        return new KeyboardBuilder()
//                .add(new KeyboardButton()
//                        .setAction(new KeyboardButtonAction()
//                                .setLabel(ACTION_CHECK_PRICE)
//                                .setType(KeyboardButtonActionType.TEXT))
//                        .setColor(KeyboardButtonColor.DEFAULT))
//                .add(new KeyboardButton()
//                        .setAction(new KeyboardButtonAction()
//                                .setLabel(ACTION_CANCEL)
//                                .setType(KeyboardButtonActionType.TEXT))
//                        .setColor(KeyboardButtonColor.NEGATIVE))
//                .build();
//    }
}
