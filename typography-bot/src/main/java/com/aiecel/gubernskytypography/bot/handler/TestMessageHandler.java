package com.aiecel.gubernskytypography.bot.handler;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.dto.ProductDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TestMessageHandler extends AbstractMessageHandler {
    @Override
    public BotMessage getDefaultResponse(Chat chat) {
        return new BotMessage(chat.getUser(), "Test");
    }

    @Override
    public BotMessage onMessage(UserMessage message, Chat chat) {
        WebClient webClient = WebClient.create("http://localhost:8085");
        Mono<ProductDTO[]> productDTOMono = webClient
                .get()
                .uri("http://localhost:8080/products/all")
                .retrieve()
                .bodyToMono(ProductDTO[].class);

        System.out.println("1");

        ProductDTO[] dtos = productDTOMono.block();
        if (dtos != null) {
            StringBuilder builder = new StringBuilder();
            for (ProductDTO product : dtos) {
                builder
                        .append(product.getName())
                        .append("\n")
                        .append(product.getDescription())
                        .append("\n")
                        .append(product.getPrice())
                        .append("\n\n");
            }
            System.out.println("2");
            return new BotMessage(chat.getUser(), builder.toString());
        }
        return new BotMessage(chat.getUser(), "No products");
    }
}
