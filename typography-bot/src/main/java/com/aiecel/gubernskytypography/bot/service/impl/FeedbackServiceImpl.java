package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.dto.FeedbackDTO;
import com.aiecel.gubernskytypography.bot.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    public static final String SEND_FEEDBACK_ENDPOINT_URL = "http://localhost:8080/feedbacks/send";

    private final WebClient webClient;

    @Override
    public void send(String feedbackText) {
        FeedbackDTO feedbackDTO = new FeedbackDTO(feedbackText);

        //register user
        Mono<FeedbackDTO> feedbackDTOMono = webClient
                .post()
                .uri(SEND_FEEDBACK_ENDPOINT_URL)
                .body(Mono.just(feedbackDTO), FeedbackDTO.class)
                .retrieve()
                .bodyToMono(FeedbackDTO.class);

        feedbackDTOMono.block(); //todo handle exceptions
        log.info("Feedback sent: {}", feedbackText);
    }
}
