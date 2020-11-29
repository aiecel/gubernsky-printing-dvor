package com.aiecel.gubernskyprintingdvor.bot.vk;

import com.aiecel.gubernskyprintingdvor.bot.MessageHandler;
import com.aiecel.gubernskyprintingdvor.bot.vk.handler.*;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class VkMessageHandlerFactory implements MessageHandlerFactory<Message> {
    @Override
    @SuppressWarnings("unchecked")
    public <T extends MessageHandler<Message>> T get(Class<T> clazz) {
        if (clazz == HomeVkMessageHandler.class) {
            return (T) getHomeVkMessageHandler();
        }
        if (clazz == OrderVkMessageHandler.class) {
            return (T) getOrderVkMessageHandler();
        }
        if (clazz == OrderDocumentVkMessageHandler.class) {
            return (T) getOrderDocumentVkMessageHandler();
        }
        if (clazz == OrderProductVkMessageHandler.class) {
            return (T) getOrderProductVkMessageHandler();
        }
        if (clazz == CommentOrderVkMessageHandler.class) {
            return (T) getCommentOrderVkMessageHandler();
        }
        if (clazz == PaymentVkMessageHandler.class) {
            return (T) getPaymentVkMessageHandler();
        }
        if (clazz == FeedbackVkMessageHandler.class) {
            return (T) getFeedbackVkMessageHandler();
        }
        return null;
    }

    @Lookup
    public HomeVkMessageHandler getHomeVkMessageHandler() {
        return null;
    }

    @Lookup
    public OrderVkMessageHandler getOrderVkMessageHandler() {
        return null;
    }

    @Lookup
    public OrderDocumentVkMessageHandler getOrderDocumentVkMessageHandler() {
        return null;
    }

    @Lookup
    public OrderProductVkMessageHandler getOrderProductVkMessageHandler() {
        return null;
    }

    @Lookup
    public CommentOrderVkMessageHandler getCommentOrderVkMessageHandler() {
        return null;
    }

    @Lookup
    public PaymentVkMessageHandler getPaymentVkMessageHandler() {
        return null;
    }

    @Lookup
    public FeedbackVkMessageHandler getFeedbackVkMessageHandler() {
        return null;
    }
}
